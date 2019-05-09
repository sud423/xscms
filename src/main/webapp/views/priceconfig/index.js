/**
 * 运费管理js
 */
setMenuCls("priceconfig");// 设置导航栏样式

$(document).ready(function() {
	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbPrice').DataTable({
			"ajax" : {
				url : basePath + "/priceconfig/list",
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
				"data" : "addr.province"
			}, {
				"data" : "addr.city"
			}, {
				"data" : "coefficient"
			}, {
				"data" : "lowestPrice"
			}, {
				"data" : "standardPrice"
			}, {
				"data" : "addFees"
			}, {
				"data" : "addTime"
			} ],
			"columnDefs" : [ {
				"render" : opt,
				"targets" : 9,
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

	} else {
		$('.selectpicker').selectpicker();
	}

	// 查询
	$("#searchPriceConfigs").bind("click", function() { // 点击按钮 触发table重新请求服务器
		window.table.ajax.reload();
	});

	// 编辑页面信息保存
	$("#priceForm").validator().on('submit', function(e) {

		if (e.isDefaultPrevented()) {
			// 处理无效的表单...
			// swal("信息", "用户名和密码不能为空", "error");
			return false;
		} else {
			$("#priceForm button").prop("disabled", true);
			$.post(basePath + "/priceconfig/save", $("#priceForm").serialize(), function(data, status) {
				if (data.code != 0) {
					swal("信息", data.result, "error");
				} else {
					swal({
						title : "信息",
						text : data.result,
						type : "success"
					}, function() {
						window.location.href = basePath + "/priceconfig/index";
					});
				}
			}).always(function() {
				$("#priceForm button").prop("disabled", false);
			});
			return false;
		}
	});
	$("input[name='lowestPrice'],input[name='standardPrice'],input[name='expenses']").TouchSpin({
		verticalbuttons : true,
		verticalupclass : 'ti-plus',
		verticaldownclass : 'ti-minus',
		min : 0,
		step : 0.01,
		max : 99999999,
		decimals : 2
	});
	$("#coefficient").TouchSpin({
		verticalbuttons : true,
		verticalupclass : 'ti-plus',
		verticaldownclass : 'ti-minus',
		min : 0,
		max : 99999999
	});

	$("#selProvince").change(function() {
		$('.selectpicker').selectpicker('refresh');
	});

});

/**
 * 连接跳转到编辑页面
 * 
 * @param id
 *            运费配置编号
 * @returns
 */
function redirect(id) {
	window.location.href = basePath + '/priceconfig/edit/' + id;
}

/**
 * 删除运费配置
 * 
 * @param id
 *            运费配置编号
 * @returns
 */
function deleteDate(id) {
	$.post(basePath + '/priceconfig/delete?priceConfigId=' + id, function(data) {
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