package com.noithat.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            // Tổng số đơn hàng
            Long totalOrders = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM donhang", Long.class);
            
            // Tổng doanh thu
            BigDecimal totalRevenue = jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(tongtien), 0) FROM donhang WHERE trangthaithanhtoan = true", 
                BigDecimal.class);
            
            // Tổng số sản phẩm
            Long totalProducts = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM sanpham WHERE trangthai = true", Long.class);
            
            // Tổng số khách hàng
            Long totalCustomers = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM khachhang", Long.class);
            
            // Đơn hàng đã thanh toán
            Long ordersPaid = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM donhang WHERE trangthaithanhtoan = true", Long.class);
            
            // Đơn hàng chờ xử lý
            Long pendingOrders = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM donhang WHERE trangthaidonhang = 'Chờ xử lý'", Long.class);
            
            stats.put("totalOrders", totalOrders != null ? totalOrders : 0);
            stats.put("totalRevenue", totalRevenue != null ? totalRevenue : BigDecimal.ZERO);
            stats.put("totalProducts", totalProducts != null ? totalProducts : 0);
            stats.put("totalCustomers", totalCustomers != null ? totalCustomers : 0);
            stats.put("ordersPaid", ordersPaid != null ? ordersPaid : 0);
            stats.put("pendingOrders", pendingOrders != null ? pendingOrders : 0);
            
        } catch (Exception e) {
            log.error("Error getting dashboard stats: {}", e.getMessage());
            // Return default values if error
            stats.put("totalOrders", 0);
            stats.put("totalRevenue", BigDecimal.ZERO);
            stats.put("totalProducts", 0);
            stats.put("totalCustomers", 0);
            stats.put("ordersPaid", 0);
            stats.put("pendingOrders", 0);
        }
        
        return stats;
    }

    public Map<String, Object> getRevenueChart(String period) {
        Map<String, Object> chartData = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<Map<String, Object>> datasets = new ArrayList<>();
        
        try {
            String sql;
            if ("month".equals(period)) {
                sql = """
                    SELECT 
                        MONTH(ngaydat) as period,
                        YEAR(ngaydat) as year,
                        COALESCE(SUM(tongtien), 0) as revenue
                    FROM donhang 
                    WHERE trangthaithanhtoan = true 
                        AND ngaydat >= DATE_SUB(NOW(), INTERVAL 12 MONTH)
                    GROUP BY YEAR(ngaydat), MONTH(ngaydat)
                    ORDER BY year, period
                    """;
            } else {
                // Default to month for now
                sql = """
                    SELECT 
                        MONTH(ngaydat) as period,
                        YEAR(ngaydat) as year,
                        COALESCE(SUM(tongtien), 0) as revenue
                    FROM donhang 
                    WHERE trangthaithanhtoan = true 
                        AND ngaydat >= DATE_SUB(NOW(), INTERVAL 12 MONTH)
                    GROUP BY YEAR(ngaydat), MONTH(ngaydat)
                    ORDER BY year, period
                    """;
            }
            
            List<Map<String, Object>> revenueData = jdbcTemplate.queryForList(sql);
            List<BigDecimal> data = new ArrayList<>();
            
            for (int i = 1; i <= 12; i++) {
                labels.add("T" + i);
                boolean found = false;
                for (Map<String, Object> row : revenueData) {
                    if (((Number) row.get("period")).intValue() == i) {
                        data.add((BigDecimal) row.get("revenue"));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    data.add(BigDecimal.ZERO);
                }
            }
            
            Map<String, Object> dataset = new HashMap<>();
            dataset.put("label", "Doanh thu (VNĐ)");
            dataset.put("data", data);
            dataset.put("borderColor", new String[]{"#FF9800"});
            dataset.put("backgroundColor", new String[]{"rgba(255, 152, 0, 0.1)"});
            dataset.put("borderWidth", 3);
            
            datasets.add(dataset);
            
        } catch (Exception e) {
            log.error("Error getting revenue chart: {}", e.getMessage());
        }
        
        chartData.put("labels", labels);
        chartData.put("datasets", datasets);
        
        return chartData;
    }

    public Map<String, Object> getOrdersChart(String period) {
        Map<String, Object> chartData = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<Map<String, Object>> datasets = new ArrayList<>();
        
        try {
            String sql = """
                SELECT 
                    MONTH(ngaydat) as period,
                    COUNT(*) as order_count
                FROM donhang 
                WHERE ngaydat >= DATE_SUB(NOW(), INTERVAL 12 MONTH)
                GROUP BY MONTH(ngaydat)
                ORDER BY period
                """;
            
            List<Map<String, Object>> orderData = jdbcTemplate.queryForList(sql);
            List<Integer> data = new ArrayList<>();
            
            for (int i = 1; i <= 12; i++) {
                labels.add("T" + i);
                boolean found = false;
                for (Map<String, Object> row : orderData) {
                    if (((Number) row.get("period")).intValue() == i) {
                        data.add(((Number) row.get("order_count")).intValue());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    data.add(0);
                }
            }
            
            Map<String, Object> dataset = new HashMap<>();
            dataset.put("label", "Số đơn hàng");
            dataset.put("data", data);
            dataset.put("backgroundColor", new String[]{"#FF9800", "#FFA726", "#FFB74D", "#FFCC02"});
            dataset.put("borderColor", "#FF9800");
            dataset.put("borderWidth", 1);
            
            datasets.add(dataset);
            
        } catch (Exception e) {
            log.error("Error getting orders chart: {}", e.getMessage());
        }
        
        chartData.put("labels", labels);
        chartData.put("datasets", datasets);
        
        return chartData;
    }

    public List<Map<String, Object>> getTopProducts(int limit) {
        List<Map<String, Object>> topProducts = new ArrayList<>();
        
        try {
            String sql = """
                SELECT 
                    sp.id,
                    sp.tensanpham as name,
                    ctsp.hinhchinh as image,
                    COALESCE(SUM(ctdh.soluong), 0) as quantity,
                    COALESCE(SUM(ctdh.soluong * ctdh.dongia), 0) as revenue
                FROM sanpham sp
                LEFT JOIN chitietsanpham ctsp ON sp.id = ctsp.sanphamid
                LEFT JOIN chitietdonhang ctdh ON ctsp.id = ctdh.chitietsanphamid
                LEFT JOIN donhang dh ON ctdh.donhangid = dh.id
                WHERE sp.trangthai = true
                    AND (dh.trangthaithanhtoan = true OR dh.id IS NULL)
                GROUP BY sp.id, sp.tensanpham, ctsp.hinhchinh
                ORDER BY quantity DESC, revenue DESC
                LIMIT ?
                """;
            
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, limit);
            
            for (Map<String, Object> row : results) {
                Map<String, Object> product = new HashMap<>();
                product.put("id", row.get("id"));
                product.put("name", row.get("name"));
                
                // Fix image path
                String hinhchinh = (String) row.get("image");
                if (hinhchinh != null && !hinhchinh.isEmpty()) {
                    product.put("image", "/assets/img/" + hinhchinh);
                } else {
                    product.put("image", "/assets/img/placeholder/product.png");
                }
                
                product.put("quantity", row.get("quantity"));
                product.put("revenue", row.get("revenue"));
                
                topProducts.add(product);
            }
            
        } catch (Exception e) {
            log.error("Error getting top products: {}", e.getMessage());
        }
        
        return topProducts;
    }

    public List<Map<String, Object>> getRecentOrders(int limit) {
        List<Map<String, Object>> recentOrders = new ArrayList<>();
        
        try {
            String sql = """
                SELECT 
                    dh.id,
                    kh.hoten as customerName,
                    dh.tongtien as total,
                    dh.trangthaidonhang as status,
                    dh.ngaydat as createdDate
                FROM donhang dh
                LEFT JOIN khachhang kh ON dh.khachhangid = kh.id
                ORDER BY dh.ngaydat DESC
                LIMIT ?
                """;
            
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, limit);
            
            for (Map<String, Object> row : results) {
                Map<String, Object> order = new HashMap<>();
                order.put("id", row.get("id"));
                order.put("customerName", row.get("customerName"));
                order.put("total", row.get("total"));
                order.put("status", row.get("status"));
                order.put("createdDate", row.get("createdDate").toString());
                
                recentOrders.add(order);
            }
            
        } catch (Exception e) {
            log.error("Error getting recent orders: {}", e.getMessage());
        }
        
        return recentOrders;
    }

    public Map<String, Object> getCategoryChart() {
        Map<String, Object> chartData = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<Map<String, Object>> datasets = new ArrayList<>();
        
        try {
            String sql = """
                SELECT 
                    dm.tendanhmuc as category_name,
                    COUNT(sp.id) as product_count
                FROM danhmuc dm
                LEFT JOIN sanpham sp ON dm.id = sp.danhmucid AND sp.trangthai = true
                GROUP BY dm.id, dm.tendanhmuc
                ORDER BY product_count DESC
                """;
            
            List<Map<String, Object>> categoryData = jdbcTemplate.queryForList(sql);
            List<Integer> data = new ArrayList<>();
            
            for (Map<String, Object> row : categoryData) {
                labels.add((String) row.get("category_name"));
                data.add(((Number) row.get("product_count")).intValue());
            }
            
            Map<String, Object> dataset = new HashMap<>();
            dataset.put("label", "Số lượng sản phẩm");
            dataset.put("data", data);
            dataset.put("backgroundColor", new String[]{
                "#FF9800", "#FFA726", "#FFB74D", "#FFCC02", 
                "#FF8F00", "#FF6F00", "#E65100"
            });
            
            datasets.add(dataset);
            
        } catch (Exception e) {
            log.error("Error getting category chart: {}", e.getMessage());
        }
        
        chartData.put("labels", labels);
        chartData.put("datasets", datasets);
        
        return chartData;
    }

    public Map<String, Object> getBusinessOverview(int months) {
        Map<String, Object> overview = new HashMap<>();
        
        try {
            // This can be extended for more detailed business metrics
            overview.put("totalRevenue", getTotalRevenue(months));
            overview.put("averageOrderValue", getAverageOrderValue(months));
            overview.put("conversionRate", getConversionRate(months));
            overview.put("customerGrowth", getCustomerGrowth(months));
            
        } catch (Exception e) {
            log.error("Error getting business overview: {}", e.getMessage());
        }
        
        return overview;
    }

    private BigDecimal getTotalRevenue(int months) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(tongtien), 0) FROM donhang WHERE trangthaithanhtoan = true AND ngaydat >= DATE_SUB(NOW(), INTERVAL ? MONTH)",
                BigDecimal.class, months);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private BigDecimal getAverageOrderValue(int months) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT COALESCE(AVG(tongtien), 0) FROM donhang WHERE trangthaithanhtoan = true AND ngaydat >= DATE_SUB(NOW(), INTERVAL ? MONTH)",
                BigDecimal.class, months);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private Double getConversionRate(int months) {
        // This is a placeholder - implement based on your business logic
        return 0.0;
    }

    private Double getCustomerGrowth(int months) {
        // This is a placeholder - implement based on your business logic
        return 0.0;
    }
}
