document.body.addEventListener("htmx:afterOnLoad", function (evt) {
  const target = evt.detail.elt.closest(".message");
  if (target) {
    target.classList.add("visible");

    setTimeout(() => {
      target.classList.remove("visible");
      target.innerHTML = "";
    }, 3000);
  }
});