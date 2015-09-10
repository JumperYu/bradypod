/**
 * @author zengxm
 * @date 2015-07-28
 * 
 */
$(function() {

	// --> ajax 初始化
	$(document).ajaxStart(function() {
		$(".ui.input").addClass("loading");
	}).ajaxStop(function() {
		$(".ui.input").removeClass("loading");
	});

	var search = function() {
		$.get("/task/listAllTasks.json", function(data) {
			html = template("table_template", data);
			$("#dataTable").html(html);
			$(".checkbox").first().checkbox({
				onChecked : function() {
					$(".checkbox").checkbox('check');
				},
				onUnchecked : function() {
					$(".checkbox").checkbox('uncheck');
				}
			});
			$(".checkbox").not(":first").checkbox({
				onChecked : function() {
					$(this).parent().parent().parent().addClass("active");
				},
				onUnchecked : function() {
					$(this).parent().parent().parent().removeClass("active");
				}
			});
		});
	}

	// --> 出发相关按钮
	$("table").on("click", "button", function(e) {
		$this = $(this);
		method = $this.data("method");
		var taskId, lastModify, _param;
		if (method == 'pause' || method == 'start') {
			// --> 启动或暂停
			_url = $this.data("url");
			taskId = $this.data("taskid");
			lastModify = $this.data("lastmodify");
			_param = {
				taskId : taskId,
				lastModify : lastModify
			};
		} else if (method == 'batch_start' || method == 'batch_pause') {
			// --> 批量启动或暂停
			_url = $this.data("url");
			taskId = [], lastModify = [];
			$("tr.active").each(function(i, tr) {
				$tr = $(tr);
				taskId.push($tr.data("taskid"));
				lastModify.push($tr.data("lastmodify"));
			});
			_param = {
				"taskId" : taskId,
				"lastModify" : lastModify
			};
			// return false;
		} else if (method == 'edit') {
			if ($(".ui.checkbox.checked").length != 1) {
				$(".ui.message p").html("请选择一个选择框");
			} else {
				$("tr.active td").each(function(i, td) {
					$td = $(td);
					$("input[name='" + $td.data("name") + "'").val($td.text());
				});
				$('.ui.modal').modal('show');
			}
			return false;
		} else if (method == 'add') {
			$('.ui.modal').modal('show');
			return false;
		} else {
			alert("请等待!");
			return false;
		}
		// 执行
		execute(_url, _param);
	});

	var execute = function(_url, _param) {
		$.ajax({
			url : _url,
			data : _param,
			dataType : "json",
			method : "POST",
			success : function(data) {
				if (data.success) {
					$(".ui.message p").html(eval("'" + data.message + "'"));
					search();
				}
			}
		});
	}
	// --> 提交修改
	$('form').on('submit', function(e) {
		e.preventDefault(); // prevent native submit
		var form = $(this).ajaxSubmit(function(data) {
			if (data.success) {
				window.location.href = "/";
			} else {
				$(".ui.message p").html(eval("'" + data.message + "'"));
			}
		});
	});

	$('.ui.menu .ui.dropdown').dropdown({
		on : 'hover'
	});

	$('.ui.menu a.item').on('click', function() {
		$(this).addClass('active').siblings().removeClass('active');
	});

	$(".ui.input").on("click", "button", function() {
		search();
	});

	search();
});