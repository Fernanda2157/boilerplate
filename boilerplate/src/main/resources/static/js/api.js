import { debounce } from './utils.js';

const API_BASE = '/api';

async function fetchData(endpoint, query = '') {
    let url = `${API_BASE}/${endpoint}`;
    
    // Append the query parameter if it exists
    if (query && endpoint.startsWith('rick-and-morty/characters')) {
    url += `?name=${encodeURIComponent(query)}`;
}
    
    const response = await fetch(url);
    // If the API returns 404 (not found), return an empty array instead of throwing
    if (response.status === 404) {
        return { results: [] }; 
    }
    if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);
    return response.json();
}

export const searchWithDebounce = debounce(async (tab, query, renderCallback) => {
    try {
        const endpoint = getEndpoint(tab);
        const data = await fetchData(endpoint, query);
        
        // If searching products locally
        let items = (tab === 'products') ? data : (data.results || []);

        if (tab === 'products' && query) {
            items = items.filter(p => p.name.toLowerCase().includes(query.toLowerCase()));
        }

        renderCallback(items);
    } catch (error) {
        console.error('Error fetching data:', error);
        renderCallback([]);
    }
}, 4500);

export async function loadTab(tab, renderCallback) {
    try {
        const endpoint = getEndpoint(tab);
        const data = await fetchData(endpoint);
        const items = (tab === 'products') ? data : (data.results || []);
        renderCallback(items);
    } catch (error) {
        console.error('Error loading initial data:', error);
        renderCallback([]);
    }
}

function getEndpoint(tab) {
    const endpoints = {
        characters: 'rick-and-morty/characters',
        locations: 'rick-and-morty/locations',
        episodes: 'rick-and-morty/episodes',
        products: 'products'
    };
    return endpoints[tab] || 'rick-and-morty/characters';
}