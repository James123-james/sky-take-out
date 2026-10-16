package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/admin/report")
@Api(tags = "数据统计")
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 营业额统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计")
    public Result<TurnoverReportVO>turnoverStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin, @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end){
        log.info("营业额统计，{}-{}",begin,end);
        return Result.success(reportService.getTurnover(begin,end));
    }

    /**
     * 用户统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/userStatistics")
    @ApiOperation("用户统计接口")
    public Result<UserReportVO>userStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin, @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end){
        log.info("用户统计，{}-{}",begin,end);
        return Result.success(reportService.getUserStatistics(begin,end));
    }
    /**
     * 订单统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/ordersStatistics")
    @ApiOperation("订单统计统计接口")
    public Result<OrderReportVO>ordersStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end){
        log.info("订单统计，{}-{}",begin,end);
        return Result.success(reportService.getOrdersStatistics(begin,end));
    }

    /**
     * 销量前十统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("top10")
    @ApiOperation("销量前十统计")
    public Result<SalesTop10ReportVO>getTop10(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                              @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end){
        log.info("菜品或套餐销量前十统计，{}-{}",begin,end);
        return Result.success(reportService.getTop10(begin,end));

    }

    /**
     * 数据报表导出
     * @return
     */
    @ApiOperation("数据报表导出")
    @GetMapping("/export")
    public Result<String>export(HttpServletResponse response){
        log.info("数据报表导出");
        reportService.export(response);
        return Result.success("导出");
    }

}
