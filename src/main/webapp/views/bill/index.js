/**
 * 账单管理js
 */
setMenuCls("bill");// 设置导航栏样式

$(document).ready(function() {

	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbBill').DataTable({
			"ajax" : {
				url : basePath + "/bill/list",
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
				"data" : "period"
			}, {
				"data" : "totalPrice"
			}, {
				"data" : "clientName"
			}, {
				"data" : "status",
				"render" : function(data, type, row) {
					switch (data) {
					case 1:
						return "未推送";
					case 10:
						return "待支付";
					case 20:
						return "已支付";
					default:
						return "";
					}
				}
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

		$("#viewPackInfo").on("show.bs.modal", function() {
			window.tbPack = $('#tbPack').DataTable({
				"bPaginate" : false,
				"ajax" : {
					url : basePath + "/bill/getItem",
					type : 'post',
					data : function(d) {
						d.billId = $("#txtBillId").val();
					}
				},
				"columns" : [ {
					"data" : "id",
					"render" : function(data, type, row, meta) {
						var seqNo = meta.settings._iDisplayStart + meta.row + 1;
						return seqNo;
					}
				}, {
					"data" : "clientName"
				}, {
					"data" : "province",
					"render" : function(data, type, row) {
						return data + row.city;
					}
				}, {
					"data" : "express",
					"render" : function(data, type, row) {
						return data +":"+ row.expressNo;
					}
				}, {
					"data" : "price"
				}, {
					"data" : "accountName"
				}, {
					"data" : "addTime"
				} ],
				"columnDefs" : [ {
					"render" : optCol,
					"targets" : 7,
					"visible" : display
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
					$(".popover").css({
						"z-index" : 1056
					});
				}
			});
		});

		$("#viewPackInfo").on("hide.bs.modal", function() {
			window.tbPack.destroy();
		});
	} else {
		$("#selClient").select2({
			language : 'zh-CN',
			// "containerCssClass":'form-control',
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

		$("#txtWeight,#txtFee").TouchSpin({
			verticalbuttons : true,
			verticalupclass : 'ti-plus',
			verticaldownclass : 'ti-minus',
			min : 0,
			step : 0.01,
			max : 99999999,
			decimals : 2
		});

		$("#mainTable tbody input").TouchSpin({
			verticalbuttons : true,
			verticalupclass : 'ti-plus',
			verticaldownclass : 'ti-minus',
			min : 0,
			max : 99999999
		});
	}

	// 查询
	$("#search").bind("click", function() { // 点击按钮 触发table重新请求服务器
		window.table.ajax.reload();
	});

	// 编辑页面信息保存
	$("#billForm").validator().on('submit', function(e) {
		if (!check())
			return false;
		if ($("input[name=totalPrice]").val() == 0) {
			swal("信息", "请先计算运费", "error");
			return false;
		}
		if (e.isDefaultPrevented()) {
			// 处理无效的表单...
			// swal("信息", "用户名和密码不能为空", "error");
			return false;
		} else {
			swal({
				title : "核对运费",
				text : "请确认运费、重量是否无误!",
				type : "warning",
				showCancelButton : true,
				closeOnConfirm : false
			}, function(isConfirm) {
				if (isConfirm) {
					$("#billForm button").prop("disabled", true);
					$.post(basePath + "/bill/save", $("#billForm").serialize(), function(data, status) {
						if (data.code != 0) {
							swal("信息", data.result, "error");
						} else {
							swal({
								title : "信息",
								text : data.result,
								type : "success"
							}, function() {
								window.location.href = basePath + "/bill/index";
							});
						}
					}).always(function() {
						$("#billForm button").prop("disabled", false);
					});
				}
			});

			return false;
		}
	});

});

function redirect(id) {
	window.location.href = basePath + '/bill/edit/' + id;
}

function addRow() {
	$("input[name=totalPrice]").val(0);
	var html = '<tr><td><input class="vertical-spin" type="text" data-bts-button-down-class="btn btn-default btn-outline" data-bts-button-up-class="btn btn-default btn-outline" autocomplete="off" name="packageDtos['
			+ i
			+ '].len" data-required-error="请输入长" value="0" /></td> <td><input class="vertical-spin" type="text" data-bts-button-down-class="btn btn-default btn-outline" data-bts-button-up-class="btn btn-default btn-outline" autocomplete="off" name="packageDtos['
			+ i
			+ '].width" data-required-error="请输入宽" value="0" /></td> <td><input class="vertical-spin" type="text" data-bts-button-down-class="btn btn-default btn-outline" data-bts-button-up-class="btn btn-default btn-outline" autocomplete="off" name="packageDtos['
			+ i
			+ '].height" data-required-error="请输入高" value="0" /></td> <td><input class="vertical-spin" type="text" data-bts-button-down-class="btn btn-default btn-outline" data-bts-button-up-class="btn btn-default btn-outline" autocomplete="off" name="packageDtos['
			+ i
			+ '].count" data-required-error="请输入件数" value="0" /></td><td><a href="javascript:void(0);" onclick="removeRow(this)"><span class="circle circle-sm bg-danger di"><i class="ti-trash"></i></span></a></td></tr>';
	var tr = $("#mainTable tbody").append(html);
	i++;
	$(tr).find("input").TouchSpin({
		verticalbuttons : true,
		verticalupclass : 'ti-plus',
		verticaldownclass : 'ti-minus',
		min : 0,
		max : 99999999
	});
}

function removeRow(o) {
	$("input[name=totalPrice]").val(0);
	if ($("#mainTable tbody").find("tr").length == 1) {
		swal("信息", "包裹信息必须 留一行", "error");
		return;
	}
	$(o).parent().parent().remove();
}

function countFee(obj) {
	if (!check())
		return;
	$(obj).prop("disabled", true);
	$.post(basePath + "/bill/countFee", $("#billForm").serialize(), function(data, status) {
		if (data) {
			if (data.totalPrice == 0) {
				swal("信息", "运费计算失败，请检查是否已配置该目地运费模板", "error");
				return;
			}
			$("#volWeight").text(data.volumeWeight);
			$("#weight").text(data.weight);
			$("#totalPrice").text(data.totalPrice);

			$("input[name='volumeWeight']").val(data.volumeWeight);
			$("input[name='weight']").val(data.weight);
			$("input[name='totalPrice']").val(data.totalPrice);
			$("input[name='totalCount']").val(data.totalCount);
		} else {
			swal("信息", "运费计算失败", "error");
		}
	}).always(function() {
		$(obj).prop("disabled", false);
	});

}

/**
 * 验证控件是否为空
 * 
 * @returns
 */
function check() {
	if ($("input[name='itemId']").val() == 0) {
		if (!$("#selClient").val()) {
			swal("信息", "请选择客户", "error");
			return false;
		}
	}
	if (!$("#selProvince").val() || !$("#selProvince").val()) {
		swal("信息", "请选择归属省市", "error");
		return false;
	}

	if (!$("#express").val()) {
		swal("信息", "请选择快递公司", "error");
		return false;
	}

	if (!$("input[name='expressNo']").val()) {
		swal("信息", "请输入快递单号", "error");
		return false;
	}

	if (!$("#txtWeight").val() || $("#txtWeight").val() <= 0) {
		swal("信息", "请输入实际重量", "error");
		return false;
	}

	var mark = true;

	$("#mainTable .vertical-spin").each(function(index, item) {
		if ($(item).val() <= 0 || !$(item).val()) {
			swal("信息", item.dataset.requiredError, "error");
			mark = false;
			return false;
		}
	})
	return mark;
}

function push(id) {
	$.post(basePath + "/bill/push?id=" + id, function(data, status) {
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
	});
}

function detailWin(id, status) {
	if (status > 1)
		display = false;
	$("#txtBillId").val(id);
	$("#viewPackInfo").modal();
}

function del(billId, itemId) {
	$.post(basePath + "/bill/del", {
		billId : billId,
		itemId : itemId
	}, function(data, status) {
		if (data.code != 0) {
			swal("信息", data.result, "error");
		} else {
			swal({
				title : "信息",
				text : data.result,
				type : "success"
			}, function() {
				clspop();
				window.tbPack.ajax.reload();
			});
		}
	});
}
