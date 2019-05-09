/**
 * 焦点事件js
 */

setMenuCls("news");// 设置导航栏样式

$(function() {
	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbNews').DataTable({
			"ajax" : {
				url : basePath + "/news/list",
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
				"data" : "target",
				"render" : function(data, type, row, meta) {
					switch (data) {
					case 1:
						return "我爱上课";
					case 2:
						return "学校";
					case 3:
						return "全部";
					}
				}
			}, {
				"data" : "title"
			}, {
				"data" : "status",
				"render" : function(data, type, full, callback) {
					switch (data) {
					case 1:
						return "发布中";
						break;
					case 10:
						return "待发布 ";
						break;
					}
				}
			}, {
				"data" : "addTime"
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
		$("#btnNewsSearch").bind("click", function() { // 点击按钮
			// 触发table重新请求服务器
			window.table.ajax.reload();
		});
	} else {

		$(".vertical-spin").TouchSpin({
			verticalbuttons : true,
			verticalupclass : 'ti-plus',
			verticaldownclass : 'ti-minus',
			min : 0,
			max : 99999999
		});

		$("#newsForm").validator().on('submit', function(e) {
			if (e.isDefaultPrevented()) {
				return false;
			} else {
				$("#newsForm button").prop("disabled", true);
				$.post(basePath + "/news/save", $("#newsForm").serialize(), function(data, status) {
					if (data.code != 0) {
						swal("信息", data.result, "error");
					} else {
						swal({
							title : "信息",
							text : data.result,
							type : "success"
						}, function() {
							window.location.href = basePath + "/news/index";
						});
					}
				}).always(function() {
					$("#newsForm button").prop("disabled", false);
				});
				return false;
			}
		});
	}
});

function redirect(id) {
	window.location.href = basePath + '/news/edit/' + id;
}

/**
 * 删除事件
 * 
 * @param id 事件编号
 * @returns
 */
function change(id) {
	$.post(basePath + '/news/change?newsId=' + id, function(data) {
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