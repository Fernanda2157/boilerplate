import { debounce } from './utils.js';

const API_BASE = '/api';

// Fetch data from our backend proxy (Avoiding direct calls to external APIs)
async function fetchData(endpoint, query = '') {
    let url = `${API_BASE}/${endpoint}`;
    if (query) {
        url += `?name=${encodeURIComponent(query)}`;
    }
    const response = await fetch(url);
    if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);
    return response.json();
}

// Debounced search function (4.5s) for the frontend
export const searchWithDebounce = debounce(async (tab, query, renderCallback) => {
    try {
        let endpoint = '';
        if (tab === 'characters') endpoint = 'rick-and-morty/characters';
        if (tab === 'locations') endpoint = 'rick-and-morty/locations';
        if (tab === 'episodes') endpoint = 'rick-and-morty/episodes';
        if (tab === 'products') endpoint = 'products';

        const data = await fetchData(endpoint, query);
        // Rick and Morty API returns data inside "results", products return an array directly
        const items = (tab === 'products') ? data : (data.results || []);
        renderCallback(items);
    } catch (error) {
        console.error('Error fetching data:', error);
        renderCallback([]);
    }
}, 4500);

// Initial load function
export async function loadTab(tab, renderCallback) {
    try {
        let endpoint = '';
        if (tab === 'characters') endpoint = 'rick-and-morty/characters';
        if (tab === 'locations') endpoint = 'rick-and-morty/locations';
        if (tab === 'episodes') endpoint = 'rick-and-morty/episodes';
        if (tab === 'products') endpoint = 'products';

        const data = await fetchData(endpoint);
        const items = (tab === 'products') ? data : (data.results || []);
        renderCallback(items);
    } catch (error) {
        console.error('Error loading initial data:', error);
    }
}