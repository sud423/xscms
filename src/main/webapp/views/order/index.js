/**
 * 订单管理js
 */
setMenuCls("order");// 设置导航栏样式

$(document).ready(function() {

	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbOrder').DataTable({
			"ajax" : {
				url : basePath + "/order/list",
				type : 'post',
				data : function(d) {
					d.keyword = $("#txtKeyword").val();
					d.isSys = true;
				}
			},
			"columns" : [ {
				"data" : "id",
				"render" : function(data, type, row, meta) {
					var seqNo = meta.settings._iDisplayStart + meta.row + 1;
					return seqNo;
				}
			}, {
				"data" : "clientName",
				"render" : function(data, type, row) {
					if (row.type == 10)
						return "业务人员下单";

					return row.clientName;
				}
			}, {
				"data" : "driverName"
			}, {
				"data" : "carNumber"
			}, {
				"data" : "goods"
			}, {
				"data" : "addr"
			}, {
				"data" : "num"
			}, {
				"data" : "weight"
			}, {
				"data" : "status",
				"render" : function(data, type, row) {
					switch (data) {
					case 1:
						return "下单成功";
					case 10:
						return "已派车";
					case 20:
						return "已接单";
					case 30:
						return "已揽件";
					default:
						return "";

					}
				}
			}, {
				"data" : "remark"
			}, {
				"data" : "addTime"
			} ],
			"columnDefs" : [ {
				"render" : opt,
				"targets" : 11,
				"visible" : visible
			} ],
			drawCallback : function() {
				$('[data-toggle="popover"]').each(function() {
					var element = $(this);
					element.popover().on("click", function() {
						clspop();
						var _this = $(this);
						setTimeout(function() {
							_this.popover("show");
						}, 100);
					});
				});
			}
		});

		$("#setcar").on("show.bs.modal", function() {
			$("select[name='driverId']").select2({
				language : 'zh-CN',
				allowClear : true,
				ajax : {
					url : basePath + '/driver/getDriver',
					dataType : 'json',
					quietMillis : 250,
					data : function(params) {
						return {
							q : params.term, // search term
						};
					},
					processResults : function(data, page) {
						return {
							results : data
						};
					},
					cache : true
				}
			});
			$("select[name='carId']").select2({
				language : 'zh-CN',
				allowClear : true,
				ajax : {
					url : basePath + '/car/getCars',
					dataType : 'json',
					quietMillis : 250,
					data : function(params) {
						return {
							q : params.term, // search term
						};
					},
					processResults : function(data, page) {
						return {
							results : data
						};
					},
					cache : true
				}
			});
		});

		$("#setcar").on("hide.bs.modal", function() {
			$("select[name='driverId']").val(null).trigger("change");
			$("select[name='carId']").val(null).trigger("change");
		});
	} else {
		$("#selDriver").select2({
			language : 'zh-CN',
			ajax : {
				url : basePath + '/driver/getAssignDriver',
				dataType : 'json',
				quietMillis : 250,
				data : function(params) {
					return {
						q : params.term, // search term
					};
				},
				processResults : function(data, page) {
					return {
						results : data
					};
				},
				cache : true
			}
		});
		$("#selClient").select2({
			language : 'zh-CN',
			ajax : {
				url : basePath + '/client/findClient',
				dataType : 'json',
				quietMillis : 250,
				data : function(params) {
					return {
						q : params.term, // search term
					};
				},
				processResults : function(data, page) {
					return {
						results : data
					};
				},
				cache : true
			}
		});
		$("#txtWeight").TouchSpin({
			verticalbuttons : true,
			verticalupclass : 'ti-plus',
			verticaldownclass : 'ti-minus',
			min : 0,
			step : 0.01,
			max : 99999999,
			decimals : 2
		});
		$("#txtNum").TouchSpin({
			verticalbuttons : true,
			verticalupclass : 'ti-plus',
			verticaldownclass : 'ti-minus',
			min : 0,
			max : 99999999
		});
	}
	// 查询
	$("#searchOrders").bind("click", function() { // 点击按钮 触发table重新请求服务器
		window.table.ajax.reload();
	});

	// 编辑页面信息保存
	$("#orderForm").validator().on('submit', function(e) {

		if (e.isDefaultPrevented()) {
			// 处理无效的表单...
			// swal("信息", "用户名和密码不能为空", "error");
			return false;
		} else {
			$("#orderForm button").prop("disabled", true);
			$.post(basePath + "/order/save", $("#orderForm").serialize(), function(data, status) {
				if (data.code != 0) {
					swal("信息", data.result, "error");
				} else {
					swal({
						title : "信息",
						text : data.result,
						type : "success"
					}, function() {
						window.location.href = basePath + "/order/index";
					});
				}
			}).always(function() {
				$("#orderForm button").prop("disabled", false);
			});
			return false;
		}
	})

});

/**
 * 作废订单
 * 
 * @param id
 *            订单编号
 * @param status
 *            40：作废
 * @returns
 */
function change(id, status) {
	$.post(basePath + '/order/change', {
		orderId : id,
		status : status
	}, function(data) {
		if (data.code != 0) {
			swal("信息", data.result, "error");
		} else {
			swal({
				title : "信息",
				text : data.result,
				type : "success"
			}, function() {
				clspop();
				window.table.ajax.reload();
			});
		}
	})
}

/**
 * 打开分配驾驶员窗体
 * 
 * @param id
 * @returns
 */
function openDriverWin(id) {
	$("#txtOrderId").val(id);
	$("#setcar").modal();
}

function saveDriver() {

	if (!$(".select2").val()) {
		swal("信息", "请选择驾驶员", "error");
		return;
	}
	$(".modal-footer button").prop("disabled", true);
	$.post(basePath + "/order/setDriver", $("#driverForm").serialize(), function(data) {
		if (data.code != 0) {
			swal("信息", data.result, "error");
		} else {
			swal({
				title : "信息",
				text : data.result,
				type : "success"
			}, function() {
				$("#setcar").modal('hide');
				window.table.ajax.reload();
			});
		}
	}).always(function() {
		$(".modal-footer button").prop("disabled", false);
	});
}