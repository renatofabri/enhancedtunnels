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