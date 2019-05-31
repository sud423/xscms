/**
 * 用户管理js
 */
setMenuCls("sysrole");// 设置导航栏样式

$(document).ready(function() {

	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbRole').DataTable({
			"ajax" : {
				url : basePath + "/sysrole/list",
				type : 'post',
				data : function(d) {
					d.keyword = $("#txtKeyword").val();
					d.isSys = true;
				}
			},
			"bPaginate" : false,
			"columns" : [ {
				"data" : "id",
				"render" : function(data, type, row, meta) {
					var seqNo = meta.settings._iDisplayStart + meta.row + 1;
					return seqNo;
				}
			}, {
				"data" : "name"
			}, {
				"data" : "alias"
			}, {
				"data" : "remark"
			}, {
				"data" : "status"
			}, {
				"data" : "addTime"
			} ],
			"columnDefs" : [ {
				"render" : function(data, type, row) {
					switch (data) {
					case 1:
						return "正常";
					case 10:
						return "禁用";
					default:
						return "";

					}
				},
				"targets" : 4
			}, {
				"render" : opt,
				"targets" : 6,
				"visible" : visible
			} ]
		});

	}

	// 查询
	$("#searchroles").bind("click", function() { // 点击按钮 触发table重新请求服务器
		window.table.ajax.reload();
	});

	// 编辑页面信息保存
	$("#roleForm").validator().on('submit', function(e) {

		if (e.isDefaultPrevented()) {
			// 处理无效的表单...
			// swal("信息", "用户名和密码不能为空", "error");
			return false;
		} else {
			$("#roleForm button").prop("disabled", true);
			$.post(basePath + "/sysrole/save", $("#roleForm").serialize(), function(data, status) {
				if (data.code != 0) {
					swal("信息", data.result, "error");
				} else {
					swal({
						title : "信息",
						text : data.result,
						type : "success"
					}, function() {
						window.location.href = basePath + "/sysrole/index";
					});
				}
			}).always(function() {
				$("#roleForm button").prop("disabled", false);
			});
			return false;
		}
	});

	$('#responsive-modal').on('show.bs.modal', function() {
		var zTree = $.fn.zTree.init($("#treeDemo"), {
			check : {
				enable : true
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onCheck : onCheck
			}
		}, zNodes);
		setCheck();
		zTree.expandAll(true);
	});
});

/**
 * 连接跳转到编辑页面
 * 
 * @param id
 *            角色编号
 * @returns
 */
function redirect(id) {
	window.location.href = basePath + '/sysrole/edit/' + id;
}

function openPermissWin(id, name) {
	$("#txtRoleId").val(id);
	$("#lblTitle").text("勾选给【" + name + "】授权")

	$("#responsive-modal").modal();

	if (arguments.length > 2) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		$.each(arguments, function(index) {
			if (typeof this != "function" && index > 0 && this) {
				var node = zTree.getNodeByParam("id", this);
				if (node != null) {
					zTree.checkNode(node, true)
				}
			}
		});
	}
}

function savePermission() {
	$(".modal-footer button").prop("disabled", true);
	$.post(basePath + "/sysrole/savepermission", $("#permissionForm").serialize(), function(data) {
		if (data.code != 0) {
			swal("信息", data.result, "error");
		} else {
			swal({
				title : "信息",
				text : "授权成功，请刷新后获取最新权限",
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

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree.getCheckedNodes(), v = [];
	nodes.sort(function compare(a, b) {
		return a.id - b.id;
	});
	for (var i = 0, l = nodes.length; i < l; i++) {
		v.push(nodes[i].id);
	}

	$("#txtPermissionIds").val(v.join(','));
}

function setCheck() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),

	type = {
		"Y" : "ps",
		"N" : "ps"
	};
	zTree.setting.check.chkboxType = type;
}