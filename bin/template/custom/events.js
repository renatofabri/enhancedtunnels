// handles the change of screen
$(".app-button").click(function() {
	$(this).parent().find('.app-active-button').removeClass('app-active-button').addClass('app-last-button');
	$(this).removeClass('app-last-button').addClass('app-active-button');
});

// handles the click on rows
$("body").on('click', '.app-server-row', function() {

	var isExpanded = $(this).attr('expanded');
    var serverCode = $(this).attr('server');

    if (typeof isExpanded !== typeof undefined && isExpanded !== false) {
        $("#tnlSrv"+$(this).attr('server')).slideUp();
        $("#cmdSrv"+$(this).attr('server')).slideUp();
        $(this).removeAttr('expanded');
    }
    else {
        $("#tnlSrv"+$(this).attr('server')).slideDown();
        $("#cmdSrv"+$(this).attr('server')).slideDown();
        $(this).attr('expanded', 'true');
    }
});

// server button behavior
function toggleServerForm() {
	var isOpen = $("#app-server-operation").attr('isOpen');
	if (typeof isOpen !== typeof undefined && isOpen !== false) {
		$("#app-server-operation").hide().removeAttr('isOpen');
		$("#app-main-view").show();
	}
	else {
		$("#app-server-operation").show().attr('isOpen', 'true');
		$("#app-main-view").hide();
	}
}

// tunnel button behavior
function toggleTunnelForm(tnlId) {
	var isOpen = $("#app-tunnel-operation").attr('isOpen');
	if (typeof isOpen !== typeof undefined && isOpen !== false) {
		$("#app-tunnel-operation").hide().removeAttr('isOpen');
		$("#tunnelServerId").attr('value', null);
		$("#app-main-view").show();
	}
	else {
		$("#app-tunnel-operation").show().attr('isOpen', 'true');
		$("#tunnelServerId").attr('value', tnlId);
		$("#app-main-view").hide();
	}
}