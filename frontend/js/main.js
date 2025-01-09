// js/main.js

document.addEventListener('DOMContentLoaded', () => {
  // Charger la barre de navigation si l'élément avec l'ID 'navbar' existe
  const navbarContainer = document.getElementById('navbar');
  if (navbarContainer) {
      fetch('navbar.html')
          .then(response => response.text())
          .then(data => {
              navbarContainer.innerHTML = data;
              setActiveLink(); // Mettre en évidence le lien actif
          })
          .catch(error => {
              console.error('Erreur lors du chargement de la barre de navigation:', error);
          });
  }
});

/**
* Mettre en évidence le lien actif dans la barre de navigation
*/
function setActiveLink() {
  const path = window.location.pathname;
  const page = path.split("/").pop();

  const links = document.querySelectorAll('.navbar-link');
  links.forEach(link => {
      const href = link.getAttribute('href');
      if (href === page || (href === 'index.html' && page === '')) {
          link.classList.add('active');
      } else {
          link.classList.remove('active');
      }
  });
}
