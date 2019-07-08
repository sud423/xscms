/**
 * 焦点事件js
 */

setMenuCls("lawyer");// 设置导航栏样式

$(function() {
	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbLawyer').DataTable({
			"ajax" : {
				url : basePath + "/lawyer/list",
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
				"data" : "name",
				"render" : function(data, type, row, meta) {
					return row.surname+data
				}
			}, {
				"data" : "cell"
			}, {
				"data" : "fixed"
			}, {
				"data" : "firm"
			}, {
				"data" : "position"
			}, {
				"data" : "practiceNo"
			}, {
				"data" : "address"
			}, {
				"data" : "wechat"
			}, {
				"data" : "qq"
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

		// 查询
		$("#btnLawyerSearch").bind("click", function() { // 点击按钮
			// 触发table重新请求服务器
			window.table.ajax.reload();
		});
	} else {


		$("#lawyerForm").validator().on('submit', function(e) {
			if (e.isDefaultPrevented()) {
				return false;
			} else {
				$("#lawyerForm button").prop("disabled", true);
				$.post(basePath + "/lawyer/save", $("#lawyerForm").serialize(), function(data, status) {
					if (data.code != 0) {
						swal("信息", data.result, "error");
					} else {
						swal({
							title : "信息",
							text : data.result,
							type : "success"
						}, function() {
							window.location.href = basePath + "/lawyer/index";
						});
					}
				}).always(function() {
					$("#lawyerForm button").prop("disabled", false);
				});
				return false;
			}
		});
	}
});

function redirect(id) {
	window.location.href = basePath + '/lawyer/edit/' + id;
}

/**
 * 删除事件
 * 
 * @param id 事件编号
 * @returns
 */
function change(id) {
	$.post(basePath + '/lawyer/change?lawyerId=' + id, function(data) {
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