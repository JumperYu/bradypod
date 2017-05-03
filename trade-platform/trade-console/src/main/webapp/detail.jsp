<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>开发样例</title>
    <meta name="renderer" content="webkit">
    <meta charset="UTF-8">
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <style>
        body {
            padding-top:50px;
            padding-bottom: 40px;
            margin-left:200px;
            -webkit-transition: margin 500ms ease;
            -moz-transition: margin 500ms ease;
            -ms-transition: margin 500ms ease;
            -o-transition: margin 500ms ease;
            transition: margin 500ms ease;
        }
        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }
        @media (max-width: 979px) {
            .navbar-fixed-top { position:fixed; }
        }
        .navbar-inverse .brand {width:180px; color:#fff; }
        .brand img {float:left; margin:2px 10px 0 0; }
        .brand .label {
            position:relative;
            left:10px;
            top:-3px;
            font-weight:normal;
            font-size:9px;
            background:#666;
            -webkit-box-shadow: inset 1px 1px 3px rgba(0, 0, 0, 0.7);
            -moz-box-shadow: inset 1px 1px 3px rgba(0, 0, 0, 0.7);
            box-shadow: inset 1px 1px 3px rgba(0, 0, 0, 0.7);
        }

        .edit .demo { margin-left:0px; margin-top:10px; padding:30px 15px 15px; border: 1px solid #DDDDDD; border-radius: 4px; position:relative; word-wrap: break-word;}
        .edit .demo:after {
            background-color: #F5F5F5;
            border: 1px solid #DDDDDD;
            border-radius: 4px 0 4px 0;
            color: #9DA0A4;
            content: "Container";
            font-size: 12px;
            font-weight: bold;
            left: -1px;
            padding: 3px 7px;
            position: absolute;
            top: -1px;
        }
        .sidebar-nav {
            position:fixed;
            width:200px;
            left:0px;
            bottom:0;
            top:44px;
            background:#ccc;
            padding: 9px 0; z-index:10;
            -webkit-transition: all 500ms ease;
            -moz-transition: all 500ms ease;
            -ms-transition: all 500ms ease;
            -o-transition: all 500ms ease;
            transition: all 500ms ease;
        }
        .sidebar-nav .nav-header { cursor:pointer; font-size:14px; color:#fff; text-shadow:0 1px 0 rgba(0, 0, 0, 0.3);}
        .sidebar-nav .nav-header span.label { font-size:10px; /*padding-bottom:0;*/ position:relative; top:-1px;}
        .sidebar-nav .nav-header i.icon-plus {}
        .sidebar-nav .nav-header .popover {color:#999; text-shadow:none;}

        .popover-info {position:relative;}
        .popover-info .popover {display:none; top: -12.5px; left:15px; }
        .popover-info:hover .popover {display:block; opacity:1; width:400px;}
        .popover-info:hover .popover .arrow {top:23px;}

        .sidebar-nav .accordion-group { border:none; }
        .boxes {}
        .sidebar-nav li { line-height:25px; }
        .sidebar-nav .box { line-height:25px; width:170px; height:25px; }
        .sidebar-nav .preview { display: block; color:#666; font-size:12px; line-height:22px;}
        .sidebar-nav .preview input { width:90px; padding:0 10px; background:#bbb; font-size:10px; color:#999; line-height:20px; height:20px; position:relative; top:-1px; }
        .sidebar-nav .view { display: none; }
        .sidebar-nav .remove,
        .sidebar-nav .configuration { display: none; }

        .sidebar-nav .boxes { display:none;}

        .demo .preview { display: none; }
        .demo .box .view { display: block; padding-top:30px;}


        .ui-draggable-dragging .view { display:block;}
        /*.demo .ui-sortable-placeholder { outline: 5px dotted #ddd; visibility: visible!Important; border-radius: 4px; }*/
        .ui-sortable-placeholder { outline: 1px dashed #ddd;visibility: visible!Important; border-radius: 4px;}
        .edit .drag { position: absolute; top: 0;right: 0; cursor: pointer; }

        .box,.lyrow { position:relative;}

        .edit .demo .lyrow .drag { top:5px; right:80px; z-index:10; }
        .edit .demo .column .box .drag { top:5px; }
        .edit .demo .column .box .configuration {position: absolute; top: 3px; right: 140px;white-space:nowrap; }
        .edit .demo .remove { position: absolute; top: 5px; right: 5px; z-index:10; }
        .demo .configuration {
            filter: alpha(opacity=0);
            opacity: 0;
            -webkit-transition: all 500ms ease;
            -moz-transition: all 500ms ease;
            -ms-transition: all 500ms ease;
            -o-transition: all 500ms ease;
            transition: all 500ms ease;
        }
        .demo .drag, .demo .remove {
            filter: alpha(opacity=20); opacity: 0.2;
            -webkit-transition: all 500ms ease;
            -moz-transition: all 500ms ease;
            -ms-transition: all 500ms ease;
            -o-transition: all 500ms ease;
            transition: all 500ms ease;
        }
        .demo .lyrow:hover > .drag,
        .demo .lyrow:hover > .configuration,
        .demo .lyrow:hover > .remove,
        .demo .box:hover .drag,
        .demo .box:hover .configuration,
        .demo .box:hover .remove { filter: alpha(opacity=100); opacity: 1; }
        .edit .demo .row-fluid:before {
            background-color: #F5F5F5;
            border: 1px solid #DDDDDD;
            border-radius: 4px 0 4px 0;
            color: #9DA0A4;
            content: "Row";
            font-size: 12px;
            font-weight: bold;
            left: -1px;
            line-height:2;
            padding: 3px 7px;
            position: absolute;
            top: -1px;
        }
        .demo .row-fluid {
            background-color: #F5F5F5;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            -webkit-box-shadow: inset 0 1px 13px rgba(0, 0, 0, 0.1);
            -moz-box-shadow: inset 0 1px 13px rgba(0, 0, 0, 0.1);
            box-shadow: inset 0 1px 13px rgba(0, 0, 0, 0.1);
            border: 1px solid #DDDDDD;
            border-radius: 4px 4px 4px 4px;
            margin: 15px 0;
            position: relative;
            padding: 25px  14px 0;
        }
        .edit .column:after {
            background-color: #F5F5F5;
            border: 1px solid #DDDDDD;
            border-radius: 4px 0 4px 0;
            color: #9DA0A4;
            content: "Column";
            font-size: 12px;
            font-weight: bold;
            left: -1px;
            padding: 3px 7px;
            position: absolute;
            top: -1px;
        }
        .column {
            background-color: #FFFFFF;
            border: 1px solid #DDDDDD;
            border-radius: 4px 4px 4px 4px;
            margin: 15px 0;
            padding: 39px 19px 24px;
            position: relative;
        }

        /* preview */
        body.devpreview { margin-left:0px;}
        .devpreview .sidebar-nav {
            left:-200px;
            -webkit-transition: all 0ms ease;
            -moz-transition: all 0ms ease;
            -ms-transition: all 0ms ease;
            -o-transition: all 0ms ease;
            transition: all 0ms ease;
        }
        .devpreview .drag, .devpreview .configuration, .devpreview .remove { display:none !Important; }
        .sourcepreview .column, .sourcepreview .row-fluid, .sourcepreview .demo .box {
            margin:0px 0;
            padding:0px;
            background:none;
            border:none;
            -webkit-box-shadow: inset 0 0px 0px rgba(0, 0, 0, 0.00);
            -moz-box-shadow: inset 0 0px 0px rgba(0, 0, 0, 0.00);
            box-shadow: inset 0 0px 0px rgba(0, 0, 0, 0.00);
        }
        .devpreview .demo .box, .devpreview .demo .row-fluid { padding-top:0; background:none; }
        .devpreview .demo .column { padding-top:19px; padding-bottom:19px; }
        #download-layout { display: none }
        #editorModal textarea,
        #downloadModal textarea { width:100%;height:280px;resize: none;-moz-box-sizing: border-box;-webkit-box-sizing: border-box;box-sizing: border-box; }
        #editorModal {width:640px;}
        a.language-selected { font-style: italic; font-weight: bold; }
    </style>
    <style>
        body{
            margin-left:120px;
            margin-right:120px;
        }
        .row-fluid .span4 img{
            width: 100%;
            height: 100%;
        }
        .span4{
        	margin-left:15px !important;
        	margin-right:15px !important;
        	
        }
    </style>
</head>
<body>
<!-- first page -->
<div class="container-fluid" style="display: block;">
    <div class="row-fluid">
        <div class="span12">
            <div class="row">
                <div class="col-md-12">
                    <div class="page-header">
                        <h1 id="title">${resp.title}</h1>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <img style="width:320px;margin:0 auto;display:block;" id="url" lt="140x140" src="${resp.url}"></img>
                </div>
                <div style="margin-top:42px;"class="col-md-6">
                    <p id="info" class="lead">价格：${resp.price}</p>
                    <p id="info" class="lead">库存：${resp.num}</p>
						<form style="" class="form-inline"
							action="order/item/${resp.itemId}" method="POST">
							<fieldset>
								<label>数量: &nbsp; &nbsp;</label><input style="width:40px;line-height:28px;" name="num" id="num" type="number" min="1" max="${resp.num}" value="1">
								<button id="subBtn" type="submit" class="btn btn-primary">提交</button>
							</fieldset>
						</form>
						 <div style="top:95px;left:169px;padding-top:8px;" id="myAlert" class="popover right">
		                    <div class="arrow"></div>
		                    <div class="popover-content">
		                        <p id="info"></p>
		                    </div>
                		</div>
					</div>
            </div>
            
        </div>
    </div>
</div>
<script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js" ></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script type="text/javascript">

	$('form').submit(function(){
		$.post($(this).attr('action'),$(this).serialize(),function(data){
			data=JSON.parse(data);
			if(!data.success){
				$('#myAlert').show().find('#info').html(data.message);
				setTimeout(function(){
					$('#myAlert').hide();
				},3000);
			}else{
				location.href='order/'+data.data.orderId
			}
		})
		return false;
	})
</script>
</body>

</html>