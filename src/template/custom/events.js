$(document).on('show.bs.modal', '.modal', function () {
    var zIndex = 1040 + (10 * $('.modal:visible').length);
    $(this).css('z-index', zIndex);
    setTimeout(function() {
        $('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
    }, 0);
});

// handles the change of screen
$(".app-button").click(function() {
	if ($(this).hasClass('app-active-button'))
		return;
	$(this).parent().find('.app-active-button').removeClass('app-active-button').addClass('app-last-button');
	$(this).removeClass('app-last-button').addClass('app-active-button');
    $("#app-about").toggle();
    $("#app-application").toggle();
});


$("#createServer").on('click', function(e){
    alert('clicked');
});

$("body").on('contextmenu', function(e) {
	if (e.ctrlKey)
		return;
	return false;
});

function getMenuPosition(mouse, direction, scrollDir, menuEl) {
    var win = $(window)[direction](),
        scroll = $(window)[scrollDir](),
        menu = menuEl[direction](),
        position = mouse + scroll;

    if (mouse + menu > win && menu < mouse)
        position -= menu;
    
    return position;
} 

function isRightClickable(element) {
	var grandparent = element.parentElement.parentElement;
	var parent = element.parentElement;
	if (grandparent != null 
			&& grandparent.getAttribute('class') != null 
			&& (grandparent.getAttribute('class').match('app-server-row')
					|| grandparent.getAttribute('class').match('app-tunnel-row'))) {
		return grandparent;
	}
	else if (parent != null 
				&& parent.getAttribute('class') != null 
				&& parent.getAttribute('class').match('app-tunnel-row')) {
		return parent;
	}
	else if (element != null 
				&& element.getAttribute('class') != null
				&& element.getAttribute('class').match('app-tunnel-row')) {
		return element;
	}
	else {
		return null;
	}
}

function hideContextMenus() {
	$("#serverContextMenu").hide();
    $("#tunnelContextMenu").hide();
}

$(".row").on('contextmenu', function(e){
    if (e.ctrlKey) 
    	return;

    var clickedElement = isRightClickable(document.elementFromPoint(e.clientX, e.clientY));
    if (clickedElement) {
    	var menuEl = null;
    	if (clickedElement.getAttribute('class').match('app-server-row')) {
    		menuEl = $("#serverContextMenu");
    	} 
    	else {
    		menuEl = $("#tunnelContextMenu");
    		console.log(clickedElement.getAttribute('launchable'));
    		if (clickedElement.getAttribute('launchable') == 'false') {
    			$("#cm-tunnel-launch").attr('onclick', '');
    			$("#cm-tunnel-launch").addClass('disabled');
    		}
    		else {
    			$("#cm-tunnel-launch").attr('onclick', 'launchFromCM(&quot;tunnel&quot;)');
    			$("#cm-tunnel-launch").removeClass('disabled');
    		}
    	}

    	menuEl.attr('code', clickedElement.getAttribute('code'));
	    menuEl.css({
	    				zIndex: 9999,
	                    left: getMenuPosition(e.clientX, 'width', 'scrollLeft', menuEl),
	                    top:  getMenuPosition(e.clientY, 'height', 'scrollTop', menuEl)
	                }).show()
	    return false;
    }
    else {
    	hideContextMenus();
    }
});


$("body").on('click', function(e) {
	hideContextMenus();
});

$("#tunnelLaunchable").change(function(){
	console.log(this.checked);
	$(".launchableCredentials").toggle(this.checked);
});

// tunnel button behavior
function toggleTunnelForm(tnlId) {
	$("#tunnelServerId").attr('value', tnlId);
	$('#app-tunnel-modal').modal('show');
}

function loadTunnelsModal(srvId) {
    $('.serverXtunnels').hide();
    $('#server'+srvId+'tunnels').show();
    // someday I'll convert all jquery here into pure js :)
    var serverName = document.getElementById('serverName'+srvId).innerHTML;
    var tunnelModalTitleElement = document.getElementById('app-tunnel-list-modal-title');
    var title = tunnelModalTitleElement.innerHTML;
    title = title.replace('{SERVER_NAME}', serverName);
    tunnelModalTitleElement.innerHTML = title;
    document.getElementById('tunnelServerId').value = srvId;
    $('#app-tunnel-list-modal').modal('show');
}

function alertModal(text) {
    alertModalButton(text, 'Close');
}

function alertModalButton(text, close) {
    alertModalComplete(text, close, 'The app says...');
}

function alertModalComplete(text, close, title) {

    var alertModalTextElement = document.getElementById('app-alert-text');
    var alertModalText = alertModalTextElement.innerHTML;
    alertModalTextElement.innerHTML = alertModalText.replace('{TEXT}', text);

    var alertModalCloseElement = document.getElementById('app-alert-close');
    var alertModalClose = alertModalCloseElement.innerHTML;
    alertModalCloseElement.innerHTML = alertModalClose.replace('{CLOSE}', close);

    var alertModalTitleElement = document.getElementById('app-alert-title');
    var alertModalTitle = alertModalTitleElement.innerHTML;
    alertModalTitleElement.innerHTML = alertModalTitle.replace('{TITLE}', title);

    $("#app-alert-modal").modal('show');
}