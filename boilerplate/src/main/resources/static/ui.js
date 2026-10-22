// Render the grid of cards
export function renderGrid(items, currentTab) {
    const grid = document.getElementById('productsGrid');
    grid.innerHTML = '';

    if (!items || items.length === 0) {
        grid.innerHTML = `<div class="col-span-full text-center py-12 text-slate-500 text-sm">No hay datos para mostrar.</div>`;
        return;
    }

    items.forEach(item => {
        const card = document.createElement('div');
        card.className = "bg-[#151c33] rounded-2xl p-4 border border-slate-800 hover:border-indigo-500/50 transition shadow-lg flex flex-col justify-between group cursor-pointer";
        
        card.onclick = () => openModal(item, currentTab);

        const imageSrc = item.image || 'https://rickandmortyapi.com/api/character/avatar/1.jpeg';
        
        card.innerHTML = `
            <div class="space-y-3">
                <img src="${imageSrc}" alt="${item.name}" class="w-full h-44 object-cover rounded-xl border border-slate-800/80" onerror="this.src='https://rickandmortyapi.com/api/character/avatar/1.jpeg';">
                <h3 class="font-bold text-white text-sm group-hover:text-indigo-400 transition line-clamp-1">${item.name || item.title}</h3>
                <p class="text-[11px] text-slate-400 line-clamp-1">${item.species || item.type || 'Sin detalles'}</p>
            </div>
            <div class="pt-3 mt-3 border-t border-slate-800/80 flex justify-between items-center text-xs">
                <span class="text-emerald-400 font-bold text-xs">${item.price ? '$' + item.price.toFixed(2) : 'Ver más'}</span>
                <span class="text-slate-500 text-[11px]">Ver Detalle →</span>
            </div>
        `;
        grid.appendChild(card);
    });
}

// Modal functions
export function openModal(item, currentTab) {
    document.getElementById('modalBadge').innerText = currentTab.toUpperCase();
    document.getElementById('modalTitle').innerText = item.name || item.title;
    document.getElementById('modalSubtitle').innerText = item.description || item.species || 'Sin descripción adicional';
    
    const modalImage = document.getElementById('modalImage');
    modalImage.src = item.image || 'https://rickandmortyapi.com/api/character/avatar/1.jpeg';
    modalImage.classList.remove('hidden');

    document.getElementById('modalExtra').innerHTML = `
        <p><strong>ID:</strong> #${item.id}</p>
        ${item.status ? `<p><strong>Status:</strong> ${item.status}</p>` : ''}
        ${item.price !== undefined ? `<p><strong>Price:</strong> $${item.price.toFixed(2)}</p>` : ''}
    `;

    document.getElementById('detailModal').classList.remove('hidden');
}

export function closeModal() {
    document.getElementById('detailModal').classList.add('hidden');
}