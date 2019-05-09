/**
 * 车辆管理js
 */
setMenuCls("car");// 设置导航栏样式

$(document).ready(function() {

	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbCar').DataTable({
			"ajax" : {
				url : basePath + "/car/list",
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
				"data" : "carNumber"
			}, {
				"data" : "type",
				"render" : function(data, type, full, callback) {
					switch (data) {
					case 1:
						return "小型货车";
						break;
					case 2:
						return "中型货车 ";
						break;
					case 3:
						return "大型货车 ";
						break;
					}
				}
			}, {
				"data" : "load"
			}, {
				"data" : "sim"
			}, {
				"data" : "status",
				"render" : function(data, type, row) {
					switch (data) {
					case 1:
						return "空闲";
					case 10:
						return "使用中";
					case 20:
						return "报废";
					default:
						return "";
					}
				}
			}, {
				"data" : "addTime"
			} ],
			"columnDefs" : [ {
				"render" : opt,
				"targets" : 7,
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
		$("#btnCarSearch").bind("click", function() { // 点击按钮
			// 触发table重新请求服务器
			window.table.ajax.reload();
		});


	} else {

		// 编辑页面信息保存
		$("#carForm").validator().on('submit', function(e) {

			if (e.isDefaultPrevented()) {
				// 处理无效的表单...
				// swal("信息", "车牌不能为空",
				// "error");
				return false;
			} else {
				$("#carForm button").prop("disabled", true);
				$.post(basePath + "/car/save", $("#carForm").serialize(), function(data, status) {
					if (data.code != 0) {
						swal("信息", data.result, "error");
					} else {
						swal({
							title : "信息",
							text : data.result,
							type : "success"
						}, function() {
							window.location.href = basePath + "/car/index";
						});
					}
				}).always(function() {
					$("#carForm button").prop("disabled", false);
				});
				return false;
			}
		});

		$(".vertical-spin").TouchSpin({
			verticalbuttons : true,
			verticalupclass : 'ti-plus',
			verticaldownclass : 'ti-minus',
			min : 0,
			max : 99999999,
			decimals : 2
		});
	}

});

function redirect(id) {
	window.location.href = basePath + '/car/edit/' + id;
}

/**
 * 删除车辆
 * 
 * @param id
 *            车辆编号
 * @param opType
 *            1：空闲 10：使用中 20：报废
 * @returns
 */
function change(id, type) {
	$.post(basePath + '/car/change?carId=' + id + '&type=' + type, function(data) {
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
