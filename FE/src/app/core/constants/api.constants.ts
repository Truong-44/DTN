export const _ENDPOINTS = {
  // Authentication
  AUTH: {
    LOGIN: 'api/taikhoan/login',
    REGISTER: 'api/taikhoan/register',
    LOGOUT: 'api/taikhoan/logout',
    CHANGE_PASSWORD: 'api/taikhoan/change-password',
    RESET_PASSWORD: 'api/taikhoan/reset-password',
    VALIDATE_TOKEN: 'api/taikhoan/validate-token',
    REFRESH: 'api/taikhoan/refresh',
  },

  // Tài khoản và phân quyền
  TAIKHOAN: 'api/taikhoan',
  QUYEN: 'api/quyen',
  KHACHHANG: 'api/khachhang',
  NHANVIEN: 'api/nhanvien',
  TAIKHOANLIENKET: 'api/taikhoanlienket',

  // Sản phẩm và danh mục
  SANPHAM: 'api/sanpham',
  CHITIETSANPHAM: 'api/chitietsanpham',
  DANHMUC: 'api/danhmuc',
  KHOHANG: 'api/khohang',

  // Giỏ hàng
  GIOHANG: 'api/giohang',
  CHITIETGIOHANG: 'api/chitietgiohang',

  // Đơn hàng
  DONHANG: 'api/donhang',
  CHITIETDONHANG: 'api/chitietdonhang',

  // Hóa đơn
  HOADON: 'api/hoadon',
  CHITIETHOADON: 'api/chitiethoadon',

  // Vận chuyển
  VANCHUYEN: 'api/vanchuyen',
  PHUONGTHUCVANCHUYEN: 'api/phuongthucvanchuyen',

  // Upload
  UPLOAD: {
    SANPHAM_IMAGE: 'upload/sanpham-image',
    BLOG_IMAGE: 'upload/blog-image',
    TREND_IMAGE: 'upload/trend-image',
  },

  // Specific API paths for complex operations
  SPECIFIC: {
    // Sản phẩm
    SANPHAM_FULL_INFO: 'api/sanpham/{id}/full',
    SANPHAM_SEARCH: 'api/sanpham/search',
    SANPHAM_SEARCH_PAGED: 'api/sanpham/search/page',
    SANPHAM_PAGED: 'api/sanpham/page',
    SANPHAM_BY_CATEGORY: 'api/sanpham/danhmuc/{danhMucId}',
    SANPHAM_AVAILABLE_COLORS: 'api/sanpham/{id}/available-colors',
    SANPHAM_AVAILABLE_SIZES: 'api/sanpham/{id}/available-sizes',
    SANPHAM_CHITIETSANPHAM: 'api/sanpham/{id}/chitietsanpham',

    // Chi tiết sản phẩm
    CHITIETSANPHAM_BY_SANPHAM: 'api/chitietsanpham/sanpham/{sanPhamId}',
    CHITIETSANPHAM_BY_SANPHAM_AND_MAU:
      'api/chitietsanpham/sanpham/{sanPhamId}/mau/{mauSac}',
    CHITIETSANPHAM_BY_SANPHAM_AND_SIZE:
      'api/chitietsanpham/sanpham/{sanPhamId}/size/{kichThuoc}',
    CHITIETSANPHAM_FILTER: 'api/chitietsanpham/sanpham/{sanPhamId}/filter',
    CHITIETSANPHAM_AVAILABLE_STOCK: 'api/chitietsanpham/available-stock',

    // Giỏ hàng
    GIOHANG_BY_KHACHHANG: 'api/giohang/khachhang/{khachHangId}',
    GIOHANG_CLEAR: 'api/giohang/{id}/clear',
    GIOHANG_CLEAR_BY_KHACHHANG: 'api/giohang/khachhang/{khachHangId}/clear',
    CHITIETGIOHANG_BY_GIOHANG: 'api/chitietgiohang/giohang/{giohangId}',
    CHITIETGIOHANG_UPDATE_SOLUONG: 'api/chitietgiohang/{id}/soluong',
    CHITIETGIOHANG_CLEAR: 'api/chitietgiohang/giohang/{giohangId}/clear',

    // Đơn hàng
    DONHANG_BY_KHACHHANG: 'api/donhang/khachhang/{khachHangId}',
    DONHANG_BY_STATUS: 'api/donhang/status/{status}',
    DONHANG_CANCEL: 'api/donhang/{id}/cancel',
    CHITIETDONHANG_BY_DONHANG: 'api/chitietdonhang/donhang/{donhangId}',
    CHITIETDONHANG_TOTAL: 'api/chitietdonhang/donhang/{donhangId}/total',
    CHITIETDONHANG_COUNT: 'api/chitietdonhang/donhang/{donhangId}/count',

    // Hóa đơn
    HOADON_BY_KHACHHANG: 'api/hoadon/khachhang/{khachHangId}',
    HOADON_BY_NHANVIEN: 'api/hoadon/nhanvien/{nhanVienId}',
    HOADON_BY_NGAY: 'api/hoadon/ngay',
    CHITIETHOADON_BY_HOADON: 'api/chitiethoadon/hoadon/{hoadonId}',
    CHITIETHOADON_SUMMARY: 'api/chitiethoadon/hoadon/{hoadonId}/summary',
    CHITIETHOADON_TOTAL: 'api/chitiethoadon/hoadon/{hoadonId}/total',

    // Kho hàng
    KHOHANG_BY_CHITIETSANPHAM: 'api/khohang/sanpham/{ctspId}',
    KHOHANG_PAGED: 'api/khohang/paged',
    KHOHANG_LOW_STOCK: 'api/khohang/low-stock',
    KHOHANG_CREATE_OR_UPDATE: 'api/khohang/create-or-update',
    KHOHANG_UPDATE_SOLUONG: 'api/khohang/{id}/soluong',
    KHOHANG_INCREASE: 'api/khohang/{id}/increase',
    KHOHANG_DECREASE: 'api/khohang/{id}/decrease',

    // Vận chuyển
    VANCHUYEN_PAGED: 'api/vanchuyen/paged',
    VANCHUYEN_ACTIVE: 'api/vanchuyen/active',
    VANCHUYEN_SEARCH: 'api/vanchuyen/search',
    VANCHUYEN_PRICE_RANGE: 'api/vanchuyen/price-range',
    VANCHUYEN_UPDATE_STATUS: 'api/vanchuyen/{id}/status',

    // Tài khoản
    TAIKHOAN_BY_USERNAME: 'api/taikhoan/username/{username}',
    TAIKHOAN_UPDATE_STATUS: 'api/taikhoan/{id}/status',

    // Khách hàng & Nhân viên
    KHACHHANG_SEARCH: 'api/khachhang/search',
    NHANVIEN_SEARCH: 'api/nhanvien/search',

    // Tài khoản liên kết
    TAIKHOANLIENKET_BY_TAIKHOAN: 'api/taikhoanlienket/taikhoan/{taiKhoanId}',
    TAIKHOANLIENKET_BY_PROVIDER: 'api/taikhoanlienket/provider/{provider}',
    TAIKHOANLIENKET_UNLINK:
      'api/taikhoanlienket/taikhoan/{taiKhoanId}/provider/{provider}',

    // Danh mục
    DANHMUC_SANPHAM: 'api/danhmuc/{id}/sanpham',

    // Status updates
    SANPHAM_UPDATE_STATUS: 'api/sanpham/{id}/status',
    DONHANG_UPDATE_STATUS: 'api/donhang/{id}/status',

    CREATE_SESSION: 'api/chat/sessions',
    GET_ALL_SESSIONS: 'api/chat/sessions',
    GET_SESSION: 'api/chat/sessions/{id}',
    END_SESSION: 'api/chat/sessions/{id}/end',

    // Session Queries
    GET_SESSIONS_DATE_RANGE: 'api/chat/sessions/date-range',
    GET_GUEST_SESSIONS: 'api/chat/sessions/guest',
    GET_SESSIONS_BY_USER: 'api/chat/sessions/user/{taiKhoanId}',
    GET_LATEST_SESSION: 'api/chat/sessions/user/{taiKhoanId}/latest',

    // Message Management
    SEND_MESSAGE: 'api/chat/messages',
    GET_MESSAGES: 'api/chat/sessions/{sessionId}/messages',
    GET_MESSAGES_PAGED: 'api/chat/sessions/{sessionId}/messages/paged',
    GET_MESSAGE_COUNT: 'api/chat/sessions/{sessionId}/message-count',
    SEARCH_MESSAGES: 'api/chat/messages/search',

    // Bot Features
    SEND_BOT_RESPONSE: 'api/chat/messages/bot-response',
    GET_BOT_SUGGESTIONS: 'api/chat/messages/bot-suggestions',
    SUGGEST_PRODUCTS: 'api/chat/sessions/{sessionId}/suggest-products',
  },
};
