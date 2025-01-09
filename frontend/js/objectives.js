// js/objectives.js

// --------------- Objectifs ----------------

// Fonction pour charger les objectifs
async function loadObjectives() {
    const errorElement = document.getElementById('objectivesError');
    errorElement.textContent = ''; // Réinitialiser les messages d'erreur

    try {
        const url = 'http://localhost:8080/objectives-service/objectives';
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Erreur HTTP: ' + response.status);
        }

        const data = await response.json(); // data est un tableau d'objets
        console.log('Objectifs:', data);

        // Sélection du <tbody> pour y injecter des lignes
        const tbody = document.querySelector('#objectivesTable tbody');
        tbody.innerHTML = ''; // Vider le tableau avant d'insérer

        data.forEach(objective => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${objective.id || 'N/A'}</td>
                <td>${capitalizeFirstLetter(objective.goalType)}</td>
                <td>${objective.startDate || 'N/A'}</td>
                <td>${objective.currentValue || 'N/A'}</td>
                <td>${objective.targetValue || 'N/A'}</td>
                <td>${objective.endDate || 'N/A'}</td>
                <td>${capitalizeFirstLetter(objective.status)}</td>
                <td>
                    ${objective.status === 'IN_PROGRESS' ? `<button class="btn" onclick="showUpdateObjectiveForm(${objective.id})">Modifier</button> ` : ''}
                    <button class="btn delete-btn" onclick="deleteObjective(${objective.id})">Supprimer</button>
                </td>
            `;
            tbody.appendChild(row);
        });
    } catch (error) {
        console.error('Erreur lors du chargement des objectifs:', error);
        errorElement.textContent = 'Erreur lors du chargement des objectifs. Voir la console pour plus de détails.';
    }
}

// Fonction pour capitaliser la première lettre
function capitalizeFirstLetter(string) {
    if (typeof string !== 'string') return '';
    return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

// Fonction pour afficher le formulaire de création d'objectif
function showCreateObjectiveForm() {
    document.getElementById('createObjectiveForm').style.display = 'block';
}

// Fonction pour cacher le formulaire de création d'objectif
function hideCreateObjectiveForm() {
    document.getElementById('createObjectiveForm').style.display = 'none';
}

// Fonction pour créer un nouvel objectif (POST)
async function createObjective(event) {
    event.preventDefault(); // Empêcher le rechargement de la page

    const goalType = document.getElementById('objectiveGoalType').value;
    const targetValue = parseFloat(document.getElementById('objectiveTargetValue').value);

    const newObjective = {
        goalType,
        targetValue,
    };

    try {
        const url = 'http://localhost:8080/objectives-service/objectives';
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newObjective)
        });

        if (!response.ok) {
            throw new Error('Erreur HTTP: ' + response.status);
        }

        const createdObjective = await response.json();
        console.log('Objectif créé:', createdObjective);

        // Recharger la liste des objectifs
        loadObjectives();
        alert('Objectif créé avec succès.');

        // Cacher le formulaire
        hideCreateObjectiveForm();
    } catch (error) {
        console.error('Erreur lors de la création de l\'objectif:', error);
        alert('Erreur lors de la création de l\'objectif. Voir la console pour plus de détails.');
    }
}

// Fonction pour supprimer un objectif (DELETE)
async function deleteObjective(id) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer cet objectif ?')) {
        return;
    }

    try {
        const url = `http://localhost:8080/objectives-service/objectives/${id}`;
        const response = await fetch(url, {
            method: 'DELETE'
        });

        if (response.status === 204) {
            console.log('Objectif supprimé avec succès.');
            // Recharger la liste des objectifs
            loadObjectives();
            alert('Objectif supprimé avec succès.');
        } else {
            throw new Error('Erreur HTTP: ' + response.status);
        }
    } catch (error) {
        console.error('Erreur lors de la suppression de l\'objectif:', error);
        alert('Erreur lors de la suppression de l\'objectif. Voir la console pour plus de détails.');
    }
}

// Fonction pour afficher le formulaire de mise à jour d'objectif
async function showUpdateObjectiveForm(id) {
    try {
        const url = `http://localhost:8080/objectives-service/objectives/${id}`;
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Erreur HTTP: ' + response.status);
        }

        const objective = await response.json();
        console.log('Objectif à mettre à jour:', objective);

        // Remplir le formulaire de mise à jour avec les données actuelles
        document.getElementById('updateObjectiveId').value = objective.id;
        document.getElementById('updateObjectiveGoalType').value = objective.goalType;
        document.getElementById('updateObjectiveTargetValue').value = objective.targetValue;

        // Afficher le formulaire de mise à jour
        document.getElementById('updateObjectiveForm').style.display = 'block';
    } catch (error) {
        console.error('Erreur lors de la récupération de l\'objectif:', error);
        alert('Erreur lors de la récupération de l\'objectif. Voir la console pour plus de détails.');
    }
}

// Fonction pour cacher le formulaire de mise à jour d'objectif
function hideUpdateObjectiveForm() {
    document.getElementById('updateObjectiveForm').style.display = 'none';
}

// Fonction pour mettre à jour un objectif (PUT)
async function updateObjective(event) {
    event.preventDefault(); // Empêcher le rechargement de la page

    const id = document.getElementById('updateObjectiveId').value;
    const goalType = document.getElementById('updateObjectiveGoalType').value;
    const targetValue = parseFloat(document.getElementById('updateObjectiveTargetValue').value);

    const updatedObjective = {
        goalType,
        targetValue,
    };

    try {
        const url = `http://localhost:8080/objectives-service/objectives/${id}`;
        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedObjective)
        });

        if (!response.ok) {
            throw new Error('Erreur HTTP: ' + response.status);
        }

        const result = await response.json();
        console.log('Objectif mis à jour:', result);

        // Recharger la liste des objectifs
        loadObjectives();

        // Cacher le formulaire de mise à jour
        hideUpdateObjectiveForm();
        alert('Objectif mis à jour avec succès.');
    } catch (error) {
        console.error('Erreur lors de la mise à jour de l\'objectif:', error);
        alert('Erreur lors de la mise à jour de l\'objectif. Voir la console pour plus de détails.');
    }
}
