/**
 * 支付折扣管理js
 */

setMenuCls("payoffer");// 设置导航栏样式

$(function() {
	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbPayOffer').DataTable({
			"ajax" : {
				url : basePath + "/payoffer/list",
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
				"data" : "express"
			}, {
				"data" : "startPeriod"
			}, {
				"data" : "endPeriod"
			}, {
				"data" : "discount"
			} ],
			"columnDefs" : [ {
				"render" : opt,
				"targets" : 5,
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

		// 查询
		$("#btnPayOffer").bind("click", function() { // 点击按钮
			// 触发table重新请求服务器
			window.table.ajax.reload();
		});
	} else {

		$("input[name='sort'],input[name='startPeriod'],input[name='endPeriod']").TouchSpin({
			verticalbuttons : true,
			verticalupclass : 'ti-plus',
			verticaldownclass : 'ti-minus',
			min : 0,
			max : 99999999
		});
		$("input[name='discount']").TouchSpin({
			verticalbuttons : true,
			verticalupclass : 'ti-plus',
			verticaldownclass : 'ti-minus',
			min : 0,
			max : 100
		});

		$("#payofferForm").validator({
			custom:{
				gt:function ($el) {
					var id = $el.data("gt") // foo
					var matchValue = $("#" + id).val();

					if (parseInt($el.val()) > 0 && parseInt(matchValue) >= parseInt($el.val())) {
						return "截止有效值不能小于起始有效值";
					}
				}
			}
		}).on('submit', function(e) {
			if (e.isDefaultPrevented()) {
				return false;
			} else {
				$("#payofferForm button").prop("disabled", true);
				$.post(basePath + "/payoffer/save", $("#payofferForm").serialize(), function(data, status) {
					if (data.code != 0) {
						swal("信息", data.result, "error");
					} else {
						swal({
							title : "信息",
							text : data.result,
							type : "success"
						}, function() {
							window.location.href = basePath + "/payoffer/index";
						});
					}
				}).always(function() {
					$("#payofferForm button").prop("disabled", false);
				});
				return false;
			}
		});
	}
});

function redirect(id) {
	window.location.href = basePath + '/payoffer/edit/' + id;
}

/**
 * 删除事件
 * 
 * @param id 事件编号
 * @returns
 */
function change(id) {
	$.post(basePath + '/payoffer/change?payofferId=' + id, function(data) {
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