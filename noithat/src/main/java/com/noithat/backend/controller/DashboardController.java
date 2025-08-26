package com.noithat.backend.controller;

import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "API dashboard quản trị")
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "Lấy thống kê tổng quan dashboard")
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardStats() {
        log.info("Getting dashboard stats");
        Map<String, Object> stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success(stats, "Lấy thống kê dashboard thành công"));
    }

    @Operation(summary = "Lấy dữ liệu biểu đồ doanh thu")
    @GetMapping("/revenue-chart")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getRevenueChart(
            @Parameter(description = "Khoảng thời gian: month, quarter, year") 
            @RequestParam(defaultValue = "month") String period) {
        log.info("Getting revenue chart data for period: {}", period);
        Map<String, Object> chartData = dashboardService.getRevenueChart(period);
        return ResponseEntity.ok(ApiResponse.success(chartData, "Lấy dữ liệu biểu đồ doanh thu thành công"));
    }

    @Operation(summary = "Lấy dữ liệu biểu đồ đơn hàng")
    @GetMapping("/orders-chart")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getOrdersChart(
            @Parameter(description = "Khoảng thời gian: month, quarter, year") 
            @RequestParam(defaultValue = "month") String period) {
        log.info("Getting orders chart data for period: {}", period);
        Map<String, Object> chartData = dashboardService.getOrdersChart(period);
        return ResponseEntity.ok(ApiResponse.success(chartData, "Lấy dữ liệu biểu đồ đơn hàng thành công"));
    }

    @Operation(summary = "Lấy danh sách sản phẩm bán chạy")
    @GetMapping("/top-products")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getTopProducts(
            @Parameter(description = "Số lượng sản phẩm muốn lấy") 
            @RequestParam(defaultValue = "5") int limit) {
        log.info("Getting top {} products", limit);
        List<Map<String, Object>> topProducts = dashboardService.getTopProducts(limit);
        return ResponseEntity.ok(ApiResponse.success(topProducts, "Lấy sản phẩm bán chạy thành công"));
    }

    @Operation(summary = "Lấy danh sách đơn hàng gần đây")
    @GetMapping("/recent-orders")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getRecentOrders(
            @Parameter(description = "Số lượng đơn hàng muốn lấy") 
            @RequestParam(defaultValue = "10") int limit) {
        log.info("Getting {} recent orders", limit);
        List<Map<String, Object>> recentOrders = dashboardService.getRecentOrders(limit);
        return ResponseEntity.ok(ApiResponse.success(recentOrders, "Lấy đơn hàng gần đây thành công"));
    }

    @Operation(summary = "Lấy dữ liệu biểu đồ phân bố danh mục")
    @GetMapping("/category-chart")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCategoryChart() {
        log.info("Getting category distribution chart data");
        Map<String, Object> chartData = dashboardService.getCategoryChart();
        return ResponseEntity.ok(ApiResponse.success(chartData, "Lấy dữ liệu biểu đồ danh mục thành công"));
    }

    @Operation(summary = "Lấy tổng quan kinh doanh theo thời gian")
    @GetMapping("/business-overview")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getBusinessOverview(
            @Parameter(description = "Số tháng muốn xem") 
            @RequestParam(defaultValue = "12") int months) {
        log.info("Getting business overview for {} months", months);
        Map<String, Object> overview = dashboardService.getBusinessOverview(months);
        return ResponseEntity.ok(ApiResponse.success(overview, "Lấy tổng quan kinh doanh thành công"));
    }
}
