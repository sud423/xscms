/**
 * 用户管理js
 */
setMenuCls("sysuser");// 设置导航栏样式

$(document).ready(function() {

	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbUser').DataTable({
			"ajax" : {
				url : basePath + "/sysuser/list",
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
				"data" : "userName"
			}, {
				"data" : "name"
			}, {
				"data" : "email"
			}, {
				"data" : "cell"
			}, {
				"data" : "status"
			}, {
				"data" : "created"
			} ],
			"columnDefs" : [ {
				"render" : function(data, type, row) {
					switch (data) {
					case 1:
						return "正常";
					case 10:
						return "已冻结";
					case 20:
						return "离职";
					default:
						return "";

					}
				},
				"targets" : 5
			}, {
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

	}

	// 查询
	$("#searchUsers").bind("click", function() { // 点击按钮 触发table重新请求服务器
		window.table.ajax.reload();
	});

	// 编辑页面信息保存
	$("#userForm").validator().on('submit', function(e) {

		if (e.isDefaultPrevented()) {
			// 处理无效的表单...
			// swal("信息", "用户名和密码不能为空", "error");
			return false;
		} else {
			$("#userForm button").prop("disabled", true);
			$.post(basePath + "/sysuser/save", $("#userForm").serialize(), function(data, status) {
				if (data.code != 0) {
					swal("信息", data.result, "error");
				} else {
					swal({
						title : "信息",
						text : data.result,
						type : "success"
					}, function() {
						window.location.href = basePath + "/sysuser/index";
					});
				}
			}).always(function() {
				$("#userForm button").prop("disabled", false);
			});
			return false;
		}
	});

	$('#responsive-modal').on('hide.bs.modal', function() {
		$('#responsive-modal input[type="checkbox"]').prop("checked", false)
	});
});

/**
 * 连接跳转到编辑页面
 * 
 * @param id
 *            用户编号
 * @returns
 */
function redirect(id) {
	window.location.href = basePath + '/sysuser/edit/' + id;
}

/**
 * 重置密码
 * 
 * @param id
 *            用户编号
 * @returns
 */
function resetPwd(id) {
	$.post(basePath + '/sysuser/reset', {
		userId : id
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
			});
		}
	})
}
/**
 * 启用/冻结
 * 
 * @param id
 *            用户编号
 * @param opType
 *            1：启用 10：冻结
 * @returns
 */
function change(id, opType) {
	$.post(basePath + '/sysuser/change', {
		userId : id,
		opType : opType
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

function openWin(id, roles) {

	$("#txtUserId").val(id);

	if (arguments.length > 1) {
		$.each(arguments, function(index) {
			if (typeof this != "function" && index > 0 && this)
				$("#chk" + this).prop("checked", true);
		});
	}
	$("#responsive-modal").modal();

}

/**
 * 保存角色 配置
 * 
 * @returns
 */
function saveRole() {
	$(".modal-footer button").prop("disabled", true);
	$.post(basePath + "/sysuser/saverole", $("#roleForm").serialize(), function(data) {
		if (data.code != 0) {
			swal("信息", data.result, "error");
		} else {
			swal({
				title : "信息",
				text : "配置成功，重新登录后生效",
				type : "success"
			}, function() {
				$("#responsive-modal").modal('hide');
				window.table.ajax.reload();
			});
		}
	}).always(function() {
		$(".modal-footer button").prop("disabled", false);
	});
}
