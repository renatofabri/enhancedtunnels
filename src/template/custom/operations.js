function resize()
{
    var heights = window.innerHeight;
    document.getElementById("app-window-main").style.height = heights + "px";
}
resize();
window.onresize = function() {
    resize();
};

function callJavaFX(text) {
    app.callFromJavascript(text);
}

function loadEditServer(id) {
    var server;
    for (var i = 0; i < serverArray.length; i++) {
        if (serverArray[i].id == id) {
            server = serverArray[i];
            break;
        }
    }
    loadServerForm(server);
}

function loadServerForm(server) {

    document.forms["serverForm"]["serverId"].value = server.id;
    document.forms["serverForm"]["serverName"].value = server.display_name;
    document.forms["serverForm"]["serverHost"].value = server.host;
    document.forms["serverForm"]["serverPort"].value = server.port;
    document.forms["serverForm"]["serverUsername"].value = server.username;
    document.forms["serverForm"]["serverPassword"].value = server.password;
    $('#app-server-modal').modal('show');
}

function saveServerForm() {

    const server = new Object();

    server.id = document.forms["serverForm"]["serverId"].value;
    server.display_name = document.forms["serverForm"]["serverName"].value;
    server.host = document.forms["serverForm"]["serverHost"].value;
    server.port = document.forms["serverForm"]["serverPort"].value;
    if (isNaN(server.port)) {
        return false;
    }
    server.username = document.forms["serverForm"]["serverUsername"].value;
    server.password = document.forms["serverForm"]["serverPassword"].value;

    app.saveServer(server);

    $('#app-server-modal').modal('hide');
    
    location.reload();
}

function loadEditTunnel(id) {
    var tunnel;
    for (var i = 0; i < tunnelArray.length; i++) {
        if (tunnelArray[i].id == id) {
            tunnel = tunnelArray[i];
            break;
        }
    }
    loadTunnelForm(tunnel);
    $('#form-tab').tab('show');
}

function loadTunnelForm(tunnel) {

    document.forms["tunnelForm"]["tunnelId"].value = tunnel.id;
    document.forms["tunnelForm"]["tunnelName"].value = tunnel.display_name;
    document.forms["tunnelForm"]["tunnelLocalPort"].value = tunnel.local_port;
    document.forms["tunnelForm"]["tunnelRemoteHost"].value = tunnel.remote_host;
    document.forms["tunnelForm"]["tunnelRemotePort"].value = tunnel.remote_port;
    document.forms["tunnelForm"]["tunnelDescription"].value = tunnel.description;
    document.forms["tunnelForm"]["tunnelServerId"].value = tunnel.parent_server;
    $('#tunnelLaunchable:checked').val(tunnel.launchable == 'true' ? true : false);
    document.forms["tunnelForm"]["tunnelUsername"].value = tunnel.username;
    document.forms["tunnelForm"]["tunnelPassword"].value = tunnel.password;
}

function saveTunnelForm() {

    const tunnel = new Object();
    
    tunnel.id = document.forms["tunnelForm"]["tunnelId"].value;
    tunnel.display_name = document.forms["tunnelForm"]["tunnelName"].value;
    tunnel.local_port = document.forms["tunnelForm"]["tunnelLocalPort"].value;
    if (isNaN(tunnel.local_port)) {
        return false;
    }
    tunnel.remote_host = document.forms["tunnelForm"]["tunnelRemoteHost"].value;
    tunnel.remote_port = document.forms["tunnelForm"]["tunnelRemotePort"].value;
    if (isNaN(tunnel.remote_port)) {
        return false;
    }
    tunnel.description = document.forms["tunnelForm"]["tunnelDescription"].value;
    tunnel.parent_server = document.forms["tunnelForm"]["tunnelServerId"].value;
    if (isNaN(tunnel.parent_server)) {
        return false;
    }

    tunnel.launchable = ($('#tunnelLaunchable:checked').val() ? "true" : "false"); // I give up...
    tunnel.username = document.forms["tunnelForm"]["tunnelUsername"].value;
    tunnel.password = document.forms["tunnelForm"]["tunnelPassword"].value;

    app.saveTunnel(tunnel);

    $('#app-tunnel-modal').modal('hide');

    location.reload();
}

function showConfirmationModal(componentType, objectId) {

    $('#app-confirmation-modal').attr('type', componentType);
    $('#app-confirmation-modal').attr('objid', objectId);


    var componentName = document.getElementById(componentType+'Name'+objectId).innerHTML;

    var confirmationMessageElement = document.getElementById('app-confirmation-text');

    var confirmationMessage = confirmationMessageElement.innerHTML;

    confirmationMessage = confirmationMessage.replace('{TYPE}', componentType.trim());
    confirmationMessage = confirmationMessage.replace('{TYPE_NAME}', componentName.trim());

    confirmationMessageElement.innerHTML = confirmationMessage;

    $('#app-confirmation-modal').modal('show');
}

function confirmDeletion() {
    var type = $('#app-confirmation-modal').attr('type');
    var id = $('#app-confirmation-modal').attr('objid');
    console.log(type + ': ' + id);

    if (type == 'server') {
        deleteServerAction(id);
    }
    else if (type == 'tunnel') {
        deleteTunnelAction(id);
    }
    else {
        var wtf = 'wtf are you doing?';
        console.log(wtf);
        alert(wtf);
    }
}

function confirmServerDeletion(id) {
	showConfirmationModal('server', id);
}

function confirmTunnelDeletion(id) {
	showConfirmationModal('tunnel', id);
}

function deleteServerAction(id) {
    console.log('Server ' + id + ' will be deleted');
    app.deleteServer(id);
    location.reload();
}

function deleteTunnelAction(id) {
    console.log('Tunnel ' + id + ' will be deleted');
    app.deleteTunnel(id);
    location.reload();
}

function launchServerAction(id) {
    app.launchServer(id);
}

function launchTunnelAction(id) {
	app.launchTunnel(id);
}


/* ContextMenu operations */
/* The following methods are wired to Server CM and Tunnel CM */

function operationDispatcherCM(origin, operation) {
	if (origin == 'server') {
		var code = $("#serverContextMenu").attr('code');
		operation[origin](code);
	}
	else if (origin == 'tunnel') {
		var code = $("#tunnelContextMenu").attr('code');
		operation[origin](code);
	}
	else {
		var msg = 'Improper use: method operationDispatcherCM was invoked with parameters ' + origin + ', ' + operation.
		console.log(msg);
		alert(msg);
	}
}

function launchFromCM(origin) {
	var operation = {};
	operation['server'] = launchServerAction;
	operation['tunnel'] = launchTunnelAction;
	operationDispatcherCM(origin, operation);
}

function deleteFromCM(origin) {
	var operation = {};
	operation['server'] = confirmServerDeletion;
	operation['tunnel'] = confirmTunnelDeletion;
	operationDispatcherCM(origin,operation);
}

function editFromCM(origin) {
	var operation = {};
	operation['server'] = loadEditServer;
	operation['tunnel'] = loadEditTunnel;
	operationDispatcherCM(origin,operation)
}


function viewTunnelsFromCM() {
	var code = $("#serverContextMenu").attr('code');
	loadTunnelsModal(code);
}