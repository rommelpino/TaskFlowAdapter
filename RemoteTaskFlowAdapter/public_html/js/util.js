function submitButton(id) {
    var button = AdfPage.PAGE.findComponentByAbsoluteId(id);
    AdfActionEvent.queue(button, true);
}