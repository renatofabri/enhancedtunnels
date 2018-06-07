function toggleSearchBox() {
	searchBox = document.getElementsByClassName("search-input")[0];
	searchToggler = document.getElementsByClassName("search-box")[0];

	if (!searchBox.classList.contains("active")) {
		searchToggler.classList.add('active');
		searchBox.classList.add('active');
		searchBox.childNodes[0].focus();
		searchToggler.childNodes[0].classList.remove('glyphicon-search');
		searchToggler.childNodes[0].classList.add('glyphicon-remove');
	} 
	else {
		searchToggler.classList.remove('active');
		searchBox.classList.remove('active');
		searchToggler.childNodes[0].classList.add('glyphicon-search');
		searchToggler.childNodes[0].classList.remove('glyphicon-remove');
	}
}