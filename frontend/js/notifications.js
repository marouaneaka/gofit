// js/notifications.js

// --------------- Notifications ----------------

// Fonction pour charger les notifications (objectifs complétés)
async function loadNotifications() {
    const errorElement = document.getElementById('notificationsError');
    const loadingIndicator = document.getElementById('loadingIndicator');
    const notificationsList = document.getElementById('notificationsList');
    errorElement.textContent = ''; // Réinitialiser les messages d'erreur
    loadingIndicator.style.display = 'block'; // Afficher l'indicateur de chargement
    notificationsList.innerHTML = ''; // Réinitialiser les notifications affichées

    try {
        // Récupérer tous les objectifs
        const getUrl = 'http://localhost:8080/objectives-service/objectives';
        const getResponse = await fetch(getUrl);

        if (!getResponse.ok) {
            throw new Error('Erreur lors de la récupération des objectifs: ' + getResponse.status);
        }

        const data = await getResponse.json(); // data est un tableau d'objets
        console.log('Objectifs:', data);

        // Filtrer les objectifs avec le statut COMPLETED
        const completedObjectives = data.filter(objective => objective.status === 'COMPLETED');

        if (completedObjectives.length === 0) {
            notificationsList.innerHTML = `<p>Aucune notification disponible.</p>`;
        } else {
            completedObjectives.forEach(objective => {
                const message = `
                    <p>🎉 Objectif "${capitalizeFirstLetter(objective.goalType)}" atteint ! 
                    Cible : ${objective.targetValue}, Date de fin : ${objective.endDate}</p>
                `;
                notificationsList.innerHTML += message;
            });
        }
    } catch (error) {
        console.error('Erreur lors du chargement des notifications:', error);
        errorElement.textContent = 'Erreur lors du chargement des notifications. Voir la console pour plus de détails.';
    } finally {
        loadingIndicator.style.display = 'none'; // Masquer l'indicateur de chargement
    }
}

// Fonction pour capitaliser la première lettre
function capitalizeFirstLetter(string) {
    if (typeof string !== 'string') return '';
    return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

