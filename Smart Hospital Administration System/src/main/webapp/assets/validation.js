function validateRegistration(form) {
    var contact = (form.contact.value || "").trim();
    if (!/^[0-9]{10,15}$/.test(contact)) {
        alert("Enter a valid contact number (10-15 digits).");
        return false;
    }
    return true;
}
