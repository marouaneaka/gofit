// js/notifications.js

// --------------- Notifications ----------------

// Fonction pour charger les notifications
async function loadNotifications() {
    const errorElement = document.getElementById('notificationsError');
    const loadingIndicator = document.getElementById('loadingIndicator');
    const notificationsList = document.getElementById('notificationsList');
    errorElement.textContent = ''; // Réinitialiser les messages d'erreur
    loadingIndicator.style.display = 'block'; // Afficher l'indicateur de chargement
    notificationsList.innerHTML = ''; // Réinitialiser les notifications affichées

    try {
        // Récupérer toutes les notifications
        const getUrl = 'http://localhost:8080/notifications-service/notifications';
        const getResponse = await fetch(getUrl);

        if (!getResponse.ok) {
            throw new Error('Erreur lors de la récupération des notifications: ' + getResponse.status);
        }

        const data = await getResponse.json(); // data est un tableau d'objets
        console.log('Notifications:', data);

        if (data.length === 0) {
            notificationsList.innerHTML = `<p>Aucune notification disponible.</p>`;
        } else {
            data.forEach(notification => {
                const message = `
                    <p>🎉 ${notification.message} 
                    Type : ${notification.notificationType}, Date : ${new Date(notification.timestamp).toLocaleString()}</p>
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