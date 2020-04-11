$(".deleteBtn").on('click', function(e) {
    if (!confirm('Are you sure you want to remove this contact?')) {
        e.preventDefault();
    }
});