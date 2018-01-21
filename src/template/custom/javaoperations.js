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

function saveServerForm() {

	const obj = new Object();
    obj.serverName = document.forms["serverForm"]["serverName"].value;
    obj.serverHost = document.forms["serverForm"]["serverHost"].value;
    obj.serverPort = document.forms["serverForm"]["serverPort"].value;
    if (isNaN(obj.serverPort)) {
    	return false;
    }
    obj.serverUsername = document.forms["serverForm"]["serverUsername"].value;
    obj.serverPassword = document.forms["serverForm"]["serverPassword"].value;

    app.saveServer(obj);

    $('#app-server-modal').modal('hide');
	
	location.reload();
}

function saveTunnelForm() {

	const obj = new Object();
	
	obj.tunnelName = document.forms["tunnelForm"]["tunnelName"].value;
	obj.tunnelLocalPort = document.forms["tunnelForm"]["tunnelLocalPort"].value;
    if (isNaN(obj.tunnelLocalPort)) {
    	return false;
    }
	obj.tunnelRemoteHost = document.forms["tunnelForm"]["tunnelRemoteHost"].value;
	obj.tunnelRemotePort = document.forms["tunnelForm"]["tunnelRemotePort"].value;
    if (isNaN(obj.tunnelRemotePort)) {
    	return false;
    }
	obj.tunnelDescription = document.forms["tunnelForm"]["tunnelDescription"].value;
	obj.tunnelParentServer = document.forms["tunnelForm"]["tunnelServerId"].value;
    if (isNaN(obj.tunnelParentServer)) {
    	return false;
    }

    app.saveTunnel(obj);

    $('#app-tunnel-modal').modal('hide');

    location.reload();
}

function deleteServerAction(id) {
	app.deleteServer(id);
	location.reload();
}

function deleteTunnelAction(id) {
	app.deleteTunnel(id);
	location.reload();
}

function launchServerAction(id) {
	app.launchServer(id);
}