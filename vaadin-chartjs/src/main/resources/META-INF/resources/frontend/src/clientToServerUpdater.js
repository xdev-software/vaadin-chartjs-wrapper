window.checkIfExistsOnClientAndUpdateServer = function checkIfExistsOnClientAndUpdateServer(id) {
	(function myLoop (i) {          
		setTimeout(function () {   
			var element = document.getElementById(id);
			if (typeof(element) != 'undefined' && element != null) {
				console.debug('checkIfExistsOnClientAndUpdateServer: Found '+ id + "; sending update to server...");
				document.getElementById(id).parentNode.$server.updateFromClient();
			}
			else {
				console.debug('checkIfExistsOnClientAndUpdateServer: Not found; Trys left: ' + i);
				if (--i) {
					myLoop(i);
				} else {
					console.warn('checkIfExistsOnClientAndUpdateServer: EXCEEDED LIMIT! Unable to find: '+ id);
				}
			}
		}, 250)
	})(20);   
}
