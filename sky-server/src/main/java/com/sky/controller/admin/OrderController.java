package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "订单管理接口")
@RestController
@RequestMapping("/admin/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 订单搜索
     * @param ordersPageQueryDTO
     * @return
     */
    @ApiOperation("订单搜索")
    @GetMapping("/conditionSearch")
    public Result<PageResult>conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO){
        log.info("订单搜索，{}",ordersPageQueryDTO);
        PageResult page=orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(page);
    }
    /**
     * 各个状态的订单数量统计
     * @return
     */
    @ApiOperation("各个状态的订单数量统计")
    @GetMapping("/statistics")
    public Result<OrderStatisticsVO>statistics(){
        OrderStatisticsVO orderStatisticsVO=orderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    @ApiOperation("查询订单详情")
    @GetMapping("/details/{id}")
    public Result<OrderVO>details(@PathVariable("id")Long id){
        log.info("查询订单详情，{}",id);
        OrderVO orderVO=orderService.details(id);
        return Result.success(orderVO);
    }

    /**
     * 接单
     * @param ordersConfirmDTO
     * @return
     */
    @ApiOperation("接单")
    @PutMapping("/confirm")
    public Result<String>confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
        log.info("接单，{}",ordersConfirmDTO);
        orderService.confirm(ordersConfirmDTO);
        return Result.success("接单");
    }

    /**
     * 拒单
     * @param ordersRejectionDTO
     * @return
     */
    @ApiOperation("拒单")
    @PutMapping("/rejection")
    public Result<String> rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        log.info("拒单，{}",ordersRejectionDTO);
        orderService.rejection(ordersRejectionDTO);
        return Result.success("拒单");
    }
    /**
     * 取消订单
     * @param ordersCancelDTO
     * @return
     */
    @ApiOperation("取消订单")
    @PutMapping("/cancel")
    public Result<String> cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) throws Exception {
        log.info("取消订单，{}",ordersCancelDTO);
        orderService.cancel(ordersCancelDTO);
        return Result.success("拒单");
    }
    /**
     * 派送订单
     * @param id
     * @return
     */
    @ApiOperation("派送订单")
    @PutMapping("/delivery/{id}")
    public Result<String> delivery(@PathVariable("id") Long id){
        log.info("派送订单，{}",id);
        orderService.delivery(id);
        return Result.success("拒单");
    }
    /**
     * 完成订单
     * @param id
     * @return
     */
    @ApiOperation("完成订单")
    @PutMapping("/complete/{id}")
    public Result<String> complete(@PathVariable("id") Long id){
        log.info("完成订单，{}",id);
        orderService.complete(id);
        return Result.success("完成订单");
    }
}
