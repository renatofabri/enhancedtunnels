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

$("#tunnelIsLaunchable").change(function(){
	console.log(this.checked);
	$("#launchableCredentials").toggle(this.checked);
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