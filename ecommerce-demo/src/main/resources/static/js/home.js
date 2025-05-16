const modal = document.getElementsByClassName("modal")[0];
const userButton = document.getElementById("user-icon");


userButton.addEventListener("click", (event) => {
  event.preventDefault();

  const displayValue = window.getComputedStyle(modal).display;
  if (displayValue === "none") {
    modal.style.display = "flex";
  } else {
    modal.style.display = "none";
  }
});

const modalCart = document.getElementsByClassName("modalCart")[0];
const cartButton = document.getElementById("cart-icon");

cartButton.addEventListener("click", (event) => {
  event.preventDefault();

  const displayValue = window.getComputedStyle(modalCart).display;
  if (displayValue === "none") {
    modalCart.style.display = "flex";
  } else {
    modalCart.style.display = "none";
  }
});

//handle click the form
document.querySelectorAll('.item form').forEach(form => {
  form.addEventListener('click', (event) => {
    // Prevent if user clicks a button or link inside, to avoid double submits
    if (event.target.tagName.toLowerCase() === 'button' ||
        event.target.tagName.toLowerCase() === 'a') {
      return;
    }
    form.submit();
  });
});

