/**
 * 焦点事件js
 */

setMenuCls("express");// 设置导航栏样式

$(function() {
	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbExpress').DataTable({
			"ajax" : {
				url : basePath + "/express/list",
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
				"data" : "name"
			}, {
				"data" : "code"
			}, {
				"data" : "coefficient"
			}, {
				"data" : "type",
				"render":function (data) {
					if (data == 10)
						return "最低收费";
					else
						return "首重+续重";
				}
			}],
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
		$("#btnExpressSearch").bind("click", function() { // 点击按钮
			// 触发table重新请求服务器
			window.table.ajax.reload();
		});
	} else {

		$("input[name='coefficient']").TouchSpin({
			verticalbuttons : true,
			verticalupclass : 'ti-plus',
			verticaldownclass : 'ti-minus',
			min : 0,
			max : 99999999
		});
		$("#expressForm").validator().on('submit', function(e) {
			if (e.isDefaultPrevented()) {
				return false;
			} else {
				$("#expressForm button").prop("disabled", true);
				$.post(basePath + "/express/save", $("#expressForm").serialize(), function(data, status) {
					if (data.code != 0) {
						swal("信息", data.result, "error");
					} else {
						swal({
							title : "信息",
							text : data.result,
							type : "success"
						}, function() {
							window.location.href = basePath + "/express/index";
						});
					}
				}).always(function() {
					$("#expressForm button").prop("disabled", false);
				});
				return false;
			}
		});
	}
});

function redirect(id) {
	window.location.href = basePath + '/express/edit/' + id;
}

/**
 * 删除事件
 * 
 * @param id
 *            事件编号
 * @returns
 */
function change(id) {
	$.post(basePath + '/express/change?dictId=' + id, function(data) {
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