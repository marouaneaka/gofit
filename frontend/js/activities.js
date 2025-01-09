// js/activities.js

// --------------- Activités ----------------


// Fonction pour charger les activités
async function loadActivities() {
    const errorElement = document.getElementById('activitiesError');
    errorElement.textContent = ''; // Réinitialiser les messages d'erreur
  
    try {
      const url = 'http://localhost:8080/activities-service/activities';
      const response = await fetch(url);
      if (!response.ok) {
        throw new Error('Erreur HTTP: ' + response.status);
      }
  
      const data = await response.json(); // data est un tableau d'objets
      console.log('Activités:', data);
  
      // Sélection du <tbody> pour y injecter des lignes
      const tbody = document.querySelector('#activitiesTable tbody');
      tbody.innerHTML = ''; // Vider le tableau avant d'insérer
  
      data.forEach(activity => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${activity.id}</td>
          <td>${activity.type}</td>
          <td>${activity.distance}</td>
          <td>${activity.duration}</td>
          <td>${activity.calories}</td>
          <td>
            <button class="btn delete-btn" onclick="deleteActivity(${activity.id})">Supprimer</button>
          </td>
        `;
        tbody.appendChild(row);
      });
    } catch (error) {
      console.error('Erreur lors du chargement des activités:', error);
      errorElement.textContent = 'Erreur lors du chargement des activités. Voir la console pour plus de détails.';
    }
  }
  
  // Fonction pour afficher le formulaire de création d'activité
  function showCreateActivityForm() {
    document.getElementById('createActivityForm').style.display = 'block';
  }
  
  // Fonction pour cacher le formulaire de création d'activité
  function hideCreateActivityForm() {
    document.getElementById('createActivityForm').style.display = 'none';
  }
  
  // Fonction pour créer une nouvelle activité (POST)
  async function createActivity(event) {
    event.preventDefault(); // Empêcher le rechargement de la page
  
    const type = document.getElementById('activityName').value;
    const distance = parseFloat(document.getElementById('activityDistance').value);
    const duration = parseFloat(document.getElementById('activityDuration').value);
    const calories = parseFloat(document.getElementById('activityCalories').value);
  
    const newActivity = {
      type,
      distance,
      duration,
      calories,
      status: 'IN_PROGRESS' // Exemple de statut par défaut
    };
  
    try {
      const url = 'http://localhost:8080/activities-service/activities';
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(newActivity)
      });
  
      if (!response.ok) {
        throw new Error('Erreur HTTP: ' + response.status);
      }
  
      const createdActivity = await response.json();
      console.log('Activité créée:', createdActivity);
  
      // Recharger la liste des activités
      loadActivities();
      alert('Activité créée avec succès.');
  
      // Cacher le formulaire
      hideCreateActivityForm();
    } catch (error) {
      console.error('Erreur lors de la création de l\'activité:', error);
      alert('Erreur lors de la création de l\'activité. Voir la console pour plus de détails.');
    }
  }

  // Fonction pour supprimer une activité (DELETE)
  async function deleteActivity(id) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer cette activité ?')) {
      return;
    }
  
    try {
      const url = `http://localhost:8080/activities-service/activities/${id}`;
      const response = await fetch(url, {
        method: 'DELETE'
      });
  
      if (response.status === 204) {
        console.log('Activité supprimée avec succès.');
        // Recharger la liste des activités
        loadActivities();
        alert('Activité supprimée avec succès.');
      } else {
        throw new Error('Erreur HTTP: ' + response.status);
      }
    } catch (error) {
      console.error('Erreur lors de la suppression de l\'activité:', error);
      alert('Erreur lors de la suppression de l\'activité. Voir la console pour plus de détails.');
    }
  }
