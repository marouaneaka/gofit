// js/statistics.js

// --------------- Statistiques ----------------

// Fonction pour charger les statistiques
async function loadStatistics() {
    const errorElement = document.getElementById('statisticsError');
    errorElement.textContent = ''; // Réinitialiser les messages d'erreur

    try {
        // 1. Envoyer une requête POST pour calculer les statistiques
        const postUrl = 'http://localhost:8080/statistics-service/statistics/';
        const postResponse = await fetch(postUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            // Si le POST ne nécessite pas de corps, vous pouvez omettre 'body'
            // body: JSON.stringify({ /* données si nécessaires */ })
        });

        if (!postResponse.ok) {
            throw new Error('Erreur lors du calcul des statistiques: ' + postResponse.status);
        }

        console.log('Calcul des statistiques effectué avec succès.');

        // 2. Envoyer une requête GET pour récupérer les statistiques calculées
        const getUrl = 'http://localhost:8080/statistics-service/statistics/';
        const getResponse = await fetch(getUrl);

        if (!getResponse.ok) {
            throw new Error('Erreur lors de la récupération des statistiques: ' + getResponse.status);
        }

        const data = await getResponse.json(); // data est un tableau d'objets
        console.log('Statistiques:', data);

        // Sélection du <tbody> pour y injecter des lignes
        const tbody = document.querySelector('#statisticsTable tbody');
        tbody.innerHTML = ''; // Vider le tableau avant d'insérer

        data.forEach(stat => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${stat.id || 'N/A'}</td>
                <td>${formatDate(stat.calculationDate)}</td>
                <td>${stat.totalCalories || 'N/A'}</td>
                <td>${stat.totalDistance || 'N/A'}</td>
                <td>${stat.totalDuration || 'N/A'}</td>
            `;
            tbody.appendChild(row);
        });
    } catch (error) {
        console.error('Erreur lors du chargement des statistiques:', error);
        errorElement.textContent = 'Erreur lors du chargement des statistiques. Voir la console pour plus de détails.';
    }
}

// Fonction pour formater la date
function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    const options = { 
        year: 'numeric', 
        month: '2-digit', 
        day: '2-digit', 
        hour: '2-digit', 
        minute: '2-digit', 
        second: '2-digit' 
    };
    return date.toLocaleDateString('fr-FR', options);
}
