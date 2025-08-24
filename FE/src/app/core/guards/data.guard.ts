import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { map, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
// Tạm thời comment các service chưa có, sẽ uncomment khi đã tạo
// import { ProductService } from '../services/product.service';
// import { OrderService } from '../services/order.service';
import { NotificationService } from '../services/notification.service';

// Guard kiểm tra sản phẩm tồn tại
export const productExistsGuard: CanActivateFn = (route, state) => {
  // const productService = inject(ProductService);
  const router = inject(Router);
  const notificationService = inject(NotificationService);

  const productId = route.paramMap.get('id');

  if (!productId || isNaN(Number(productId))) {
    notificationService.error('Lỗi', 'ID sản phẩm không hợp lệ');
    router.navigate(['/products']);
    return false;
  }

  // TODO: Implement khi đã có ProductService
  // return productService.getSanPhamById(Number(productId)).pipe(
  //   map(product => {
  //     if (product) {
  //       return true;
  //     } else {
  //       notificationService.error('Không tìm thấy', 'Sản phẩm không tồn tại');
  //       router.navigate(['/products']);
  //       return false;
  //     }
  //   }),
  //   catchError(error => {
  //     console.error('Error checking product existence:', error);
  //     notificationService.error('Lỗi', 'Không thể kiểm tra sản phẩm');
  //     router.navigate(['/products']);
  //     return of(false);
  //   })
  // );

  // Tạm thời return true
  return true;
};

// Guard kiểm tra đơn hàng tồn tại
export const orderExistsGuard: CanActivateFn = (route, state) => {
  // const orderService = inject(OrderService);
  const router = inject(Router);
  const notificationService = inject(NotificationService);

  const orderId = route.paramMap.get('id');

  if (!orderId || isNaN(Number(orderId))) {
    notificationService.error('Lỗi', 'ID đơn hàng không hợp lệ');
    router.navigate(['/orders']);
    return false;
  }

  // TODO: Implement khi đã có OrderService
  // return orderService.getDonHangById(Number(orderId)).pipe(
  //   map(order => {
  //     if (order) {
  //       return true;
  //     } else {
  //       notificationService.error('Không tìm thấy', 'Đơn hàng không tồn tại');
  //       router.navigate(['/orders']);
  //       return false;
  //     }
  //   }),
  //   catchError(error => {
  //     console.error('Error checking order existence:', error);
  //     notificationService.error('Lỗi', 'Không thể kiểm tra đơn hàng');
  //     router.navigate(['/orders']);
  //     return of(false);
  //   })
  // );

  // Tạm thời return true
  return true;
};

// Guard kiểm tra quyền sở hữu đơn hàng (chỉ customer sở hữu hoặc admin/employee mới xem được)
export const orderOwnershipGuard: CanActivateFn = (route, state) => {
  // const orderService = inject(OrderService);
  const router = inject(Router);
  const notificationService = inject(NotificationService);

  const orderId = route.paramMap.get('id');

  if (!orderId || isNaN(Number(orderId))) {
    notificationService.error('Lỗi', 'ID đơn hàng không hợp lệ');
    router.navigate(['/orders']);
    return false;
  }

  // TODO: Implement khi đã có OrderService và AuthService hoàn chỉnh
  // return orderService.getDonHangById(Number(orderId)).pipe(
  //   map(order => {
  //     // Logic kiểm tra ownership sẽ được implement dựa trên user hiện tại
  //     return true;
  //   }),
  //   catchError(error => {
  //     console.error('Error checking order ownership:', error);
  //     notificationService.error('Lỗi', 'Không thể kiểm tra quyền sở hữu đơn hàng');
  //     router.navigate(['/orders']);
  //     return of(false);
  //   })
  // );

  // Tạm thời return true
  return true;
};
