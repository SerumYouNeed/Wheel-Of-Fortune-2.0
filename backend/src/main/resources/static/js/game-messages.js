document.body.addEventListener("htmx:afterSwap", function (evt) {
  const target = evt.target.closest(".message");
  if (target) {
    // show (fade in)
    target.classList.add("visible");

    setTimeout(() => {
      target.classList.remove("visible");

      // after transition (0.5s) remove text
      setTimeout(() => {
        target.innerHTML = "";
      }, 500);
    }, 3000);
  }
});

document.body.addEventListener("htmx:afterSwap", (event) => {
if (event.target.matches(".puzzle")) {
  htmx.ajax('GET', '/game/balance', {target: '#player-balance'});
}
});
