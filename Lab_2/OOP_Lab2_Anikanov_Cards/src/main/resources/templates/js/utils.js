function redirectTo(urlWithPlaceholder, ...params) {
    let url = urlWithPlaceholder.format(params);
    window.location.href = url;
}

function sendReq(method, urlWithPlaceholder, ...params) {
    let url = urlWithPlaceholder.format(...params);
    $.ajax({
        url: url,
        type: method,
        success: function (result) {
            alert(`${method} call to ${url} succeeded!`);
        },
        error: function (jqXhr, textStatus, errorMessage){
            console.log(`${method} ${url} Error: ${jqXhr} ${textStatus} ${errorMessage}`);
            alert(`${method} call to ${url} failed!${textStatus} ${errorMessage}`);
        }
    });
}

function getTextById(elementId) {
    return $("#" + elementId).val();
}

function isChecked(elementId) {
       return $("#" + elementId).is(':checked');
}

function init() {
    if (!String.prototype.format) {
        String.prototype.format = function () {
            var args = arguments;
            return this.replace(/{(\d+)}/g, function (match, number) {
                return typeof args[number] != 'undefined' ?
                    args[number] : match;
            });
        };
    }
}

init();