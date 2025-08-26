import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormsModule,
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { KhachHangService } from '../../../../core/services/khachhang.service';
import { KhachHang } from '../../../../core/models/khachhang.model';
import { NotificationService } from '../../../../core/services/notification.service';
import { LoadingService } from '../../../../core/services/loading.service';

@Component({
  selector: 'app-customer-edit',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterModule],
  templateUrl: './customer-edit.component.html',
  styleUrls: ['./customer-edit.component.scss'],
})
export class CustomerEditComponent implements OnInit {
  customerForm!: FormGroup;
  customer: KhachHang | null = null;
  loading = false;
  customerId: number = 0;
  isEditMode = false;
  submitted = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private khachHangService: KhachHangService,
    private notificationService: NotificationService,
    private loadingService: LoadingService
  ) {
    this.initForm();
  }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.customerId = +params['id'];
      this.isEditMode = !!this.customerId;

      if (this.isEditMode) {
        this.loadCustomerData();
      }
    });
  }

  initForm() {
    this.customerForm = this.fb.group({
      hoten: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(100),
        ],
      ],
      email: ['', [Validators.email, Validators.maxLength(100)]],
      sodienthoai: ['', [Validators.pattern(/^[0-9]{10,11}$/)]],
      diachi: ['', [Validators.maxLength(255)]],
      ngaysinh: [''],
      gioitinh: ['Nam', Validators.required],
      tendangnhap: ['', [Validators.maxLength(50)]],
      ghichu: ['', [Validators.maxLength(500)]],
    });
  }

  loadCustomerData() {
    this.loading = true;
    this.loadingService.show();

    this.khachHangService
      .getById(this.customerId)
      .pipe(
        catchError((error) => {
          console.error('Error loading customer:', error);
          this.notificationService.error(
            'Lỗi',
            'Không thể tải thông tin khách hàng'
          );
          return of(null);
        })
      )
      .subscribe({
        next: (customer) => {
          if (customer) {
            this.customer = customer;
            this.populateForm(customer);
          } else {
            this.notificationService.error('Lỗi', 'Không tìm thấy khách hàng');
            this.goBack();
          }
          this.loading = false;
          this.loadingService.hide();
        },
        error: (error: any) => {
          console.error('Error loading customer:', error);
          this.notificationService.error(
            'Lỗi',
            'Không thể tải thông tin khách hàng'
          );
          this.loading = false;
          this.loadingService.hide();
          this.goBack();
        },
      });
  }

  populateForm(customer: KhachHang) {
    this.customerForm.patchValue({
      hoten: customer.hoten || '',
      email: customer.email || '',
      sodienthoai: customer.sodienthoai || '',
      diachi: customer.diachi || '',
      ngaysinh: customer.ngaysinh
        ? this.formatDateForInput(customer.ngaysinh)
        : '',
      gioitinh: customer.gioitinh || 'Nam',
      tendangnhap: customer.tendangnhap || '',
      ghichu: customer.ghichu || '',
    });
  }

  formatDateForInput(date: Date | string): string {
    if (!date) return '';

    try {
      const d = typeof date === 'string' ? new Date(date) : date;
      return d.toISOString().split('T')[0];
    } catch {
      return '';
    }
  }

  onSubmit() {
    this.submitted = true;

    if (this.customerForm.valid) {
      const formData = this.customerForm.value;

      // Convert date string back to Date object
      if (formData.ngaysinh) {
        formData.ngaysinh = new Date(formData.ngaysinh);
      }

      if (this.isEditMode) {
        this.updateCustomer(formData);
      } else {
        this.createCustomer(formData);
      }
    } else {
      this.notificationService.error(
        'Lỗi',
        'Vui lòng kiểm tra lại thông tin đã nhập'
      );
      this.markFormGroupTouched();
    }
  }

  createCustomer(customerData: Partial<KhachHang>) {
    this.loadingService.show();

    this.khachHangService.create(customerData).subscribe({
      next: () => {
        this.notificationService.success('Thành công', 'Đã tạo khách hàng mới');
        this.goBack();
      },
      error: (error: any) => {
        console.error('Error creating customer:', error);
        this.notificationService.error('Lỗi', 'Không thể tạo khách hàng mới');
        this.loadingService.hide();
      },
    });
  }

  updateCustomer(customerData: Partial<KhachHang>) {
    this.loadingService.show();

    this.khachHangService.update(this.customerId, customerData).subscribe({
      next: () => {
        this.notificationService.success(
          'Thành công',
          'Đã cập nhật thông tin khách hàng'
        );
        this.goBack();
      },
      error: (error: any) => {
        console.error('Error updating customer:', error);
        this.notificationService.error(
          'Lỗi',
          'Không thể cập nhật thông tin khách hàng'
        );
        this.loadingService.hide();
      },
    });
  }

  markFormGroupTouched() {
    Object.keys(this.customerForm.controls).forEach((key) => {
      this.customerForm.get(key)?.markAsTouched();
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.customerForm.get(fieldName);
    return !!(
      field &&
      field.invalid &&
      (field.dirty || field.touched || this.submitted)
    );
  }

  getFieldError(fieldName: string): string {
    const field = this.customerForm.get(fieldName);

    if (field?.errors) {
      if (field.errors['required']) {
        return `${this.getFieldLabel(fieldName)} là bắt buộc`;
      }
      if (field.errors['email']) {
        return 'Email không hợp lệ';
      }
      if (field.errors['minlength']) {
        return `${this.getFieldLabel(fieldName)} phải có ít nhất ${
          field.errors['minlength'].requiredLength
        } ký tự`;
      }
      if (field.errors['maxlength']) {
        return `${this.getFieldLabel(fieldName)} không được vượt quá ${
          field.errors['maxlength'].requiredLength
        } ký tự`;
      }
      if (field.errors['pattern']) {
        return 'Số điện thoại không hợp lệ (10-11 số)';
      }
    }

    return '';
  }

  getFieldLabel(fieldName: string): string {
    const labels: { [key: string]: string } = {
      hoten: 'Họ và tên',
      email: 'Email',
      sodienthoai: 'Số điện thoại',
      diachi: 'Địa chỉ',
      ngaysinh: 'Ngày sinh',
      gioitinh: 'Giới tính',
      tendangnhap: 'Tên đăng nhập',
      ghichu: 'Ghi chú',
    };
    return labels[fieldName] || fieldName;
  }

  goBack() {
    if (this.isEditMode && this.customer) {
      this.router.navigate([
        '/admin/customer-management/detail',
        this.customer.id,
      ]);
    } else {
      this.router.navigate(['/admin/customer-management']);
    }
  }

  resetForm() {
    if (this.isEditMode && this.customer) {
      this.populateForm(this.customer);
    } else {
      this.customerForm.reset();
      this.customerForm.patchValue({ gioitinh: 'Nam' });
    }
    this.submitted = false;
  }

  getFormErrors(): string[] {
    const errors: string[] = [];
    Object.keys(this.customerForm.controls).forEach((key) => {
      const control = this.customerForm.get(key);
      if (
        control &&
        control.invalid &&
        (control.dirty || control.touched || this.submitted)
      ) {
        errors.push(this.getFieldError(key));
      }
    });
    return errors;
  }
}
