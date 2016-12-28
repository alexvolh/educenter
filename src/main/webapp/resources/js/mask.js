function setMask() {
    var c = PF('cbxMask');
    var i = PF('timeMask');
    if (c.isChecked()) {
        i.jq.mask('999 часов');
        i.jq.focus();
    } else {
        i.jq.mask('99 часов');
        i.jq.focus();
    }

}