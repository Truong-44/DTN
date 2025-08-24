// core/services/validation.service.ts
import { Injectable } from '@angular/core';

export interface ValidationResult {
  isValid: boolean;
  errors: string[];
}

@Injectable({
  providedIn: 'root',
})
export class ValidationService {
  // Email validation
  validateEmail(email: string): ValidationResult {
    const errors: string[] = [];

    if (!email) {
      errors.push('Email là bắt buộc');
    } else {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(email)) {
        errors.push('Email không đúng định dạng');
      }
    }

    return { isValid: errors.length === 0, errors };
  }

  // Phone validation
  validatePhone(phone: string): ValidationResult {
    const errors: string[] = [];

    if (!phone) {
      errors.push('Số điện thoại là bắt buộc');
    } else {
      const phoneRegex = /^(\+84|84|0)[3-9]\d{8}$/;
      if (!phoneRegex.test(phone)) {
        errors.push('Số điện thoại không đúng định dạng (VD: 0912345678)');
      }
    }

    return { isValid: errors.length === 0, errors };
  }

  // Password validation
  validatePassword(password: string): ValidationResult {
    const errors: string[] = [];

    if (!password) {
      errors.push('Mật khẩu là bắt buộc');
    } else {
      if (password.length < 6) {
        errors.push('Mật khẩu phải có ít nhất 6 ký tự');
      }
      if (password.length > 50) {
        errors.push('Mật khẩu không được quá 50 ký tự');
      }
      // Có thể thêm các yêu cầu phức tạp hơn
      // if (!/(?=.*[a-z])(?=.*[A-Z])(?=.*\d)/.test(password)) {
      //   errors.push('Mật khẩu phải chứa ít nhất 1 chữ thường, 1 chữ hoa và 1 số');
      // }
    }

    return { isValid: errors.length === 0, errors };
  }

  // Confirm password validation
  validateConfirmPassword(
    password: string,
    confirmPassword: string
  ): ValidationResult {
    const errors: string[] = [];

    if (!confirmPassword) {
      errors.push('Xác nhận mật khẩu là bắt buộc');
    } else if (password !== confirmPassword) {
      errors.push('Mật khẩu xác nhận không khớp');
    }

    return { isValid: errors.length === 0, errors };
  }

  // Required field validation
  validateRequired(value: any, fieldName: string): ValidationResult {
    const errors: string[] = [];

    if (!value || (typeof value === 'string' && value.trim() === '')) {
      errors.push(`${fieldName} là bắt buộc`);
    }

    return { isValid: errors.length === 0, errors };
  }

  // String length validation
  validateLength(
    value: string,
    min: number,
    max: number,
    fieldName: string
  ): ValidationResult {
    const errors: string[] = [];

    if (value) {
      if (value.length < min) {
        errors.push(`${fieldName} phải có ít nhất ${min} ký tự`);
      }
      if (value.length > max) {
        errors.push(`${fieldName} không được quá ${max} ký tự`);
      }
    }

    return { isValid: errors.length === 0, errors };
  }

  // Number validation
  validateNumber(
    value: any,
    min?: number,
    max?: number,
    fieldName?: string
  ): ValidationResult {
    const errors: string[] = [];
    const name = fieldName || 'Giá trị';

    if (value === null || value === undefined || value === '') {
      errors.push(`${name} là bắt buộc`);
    } else {
      const numValue = Number(value);
      if (isNaN(numValue)) {
        errors.push(`${name} phải là số`);
      } else {
        if (min !== undefined && numValue < min) {
          errors.push(`${name} phải lớn hơn hoặc bằng ${min}`);
        }
        if (max !== undefined && numValue > max) {
          errors.push(`${name} phải nhỏ hơn hoặc bằng ${max}`);
        }
      }
    }

    return { isValid: errors.length === 0, errors };
  }

  // Positive number validation
  validatePositiveNumber(value: any, fieldName?: string): ValidationResult {
    return this.validateNumber(value, 0.01, undefined, fieldName);
  }

  // Integer validation
  validateInteger(
    value: any,
    min?: number,
    max?: number,
    fieldName?: string
  ): ValidationResult {
    const errors: string[] = [];
    const name = fieldName || 'Giá trị';

    const numberResult = this.validateNumber(value, min, max, name);
    if (!numberResult.isValid) {
      return numberResult;
    }

    const numValue = Number(value);
    if (!Number.isInteger(numValue)) {
      errors.push(`${name} phải là số nguyên`);
    }

    return { isValid: errors.length === 0, errors };
  }

  // Price validation (for product prices)
  validatePrice(price: any): ValidationResult {
    return this.validateNumber(price, 0, 999999999, 'Giá');
  }

  // Quantity validation
  validateQuantity(quantity: any): ValidationResult {
    return this.validateInteger(quantity, 1, 9999, 'Số lượng');
  }

  // Combine multiple validation results
  combineValidationResults(...results: ValidationResult[]): ValidationResult {
    const allErrors: string[] = [];
    let isValid = true;

    results.forEach((result) => {
      if (!result.isValid) {
        isValid = false;
        allErrors.push(...result.errors);
      }
    });

    return { isValid, errors: allErrors };
  }

  // Validate product data
  validateSanPham(sanpham: any): ValidationResult {
    return this.combineValidationResults(
      this.validateRequired(sanpham.tensanpham, 'Tên sản phẩm'),
      this.validateLength(sanpham.tensanpham, 2, 200, 'Tên sản phẩm'),
      this.validatePrice(sanpham.gia),
      this.validateRequired(sanpham.danhMucId, 'Danh mục'),
      this.validateLength(sanpham.mota, 0, 1000, 'Mô tả')
    );
  }

  // Validate customer data
  validateKhachHang(khachhang: any): ValidationResult {
    return this.combineValidationResults(
      this.validateRequired(khachhang.hoten, 'Họ tên'),
      this.validateLength(khachhang.hoten, 2, 100, 'Họ tên'),
      this.validateEmail(khachhang.email),
      this.validatePhone(khachhang.sodienthoai),
      this.validateLength(khachhang.diachi, 0, 500, 'Địa chỉ')
    );
  }
}
