/**
 * Renders the grid of cards dynamically based on the active tab.
 */
export function renderGrid(items, currentTab) {
    const grid = document.getElementById('productsGrid');
    grid.innerHTML = '';

    if (!items || items.length === 0) {
        grid.innerHTML = `<div class="col-span-full text-center py-12 text-slate-500 text-sm">No data found.</div>`;
        return;
    }

    items.forEach(item => {
        const card = document.createElement('div');
        card.className = "bg-[#151c33] rounded-2xl p-4 border border-slate-800 hover:border-indigo-500/50 transition shadow-lg flex flex-col justify-between group cursor-pointer";
        
        // Open detail modal if clicking anywhere but a button
        card.onclick = (e) => {
            if (e.target.closest('button')) return; 
            openModal(item, currentTab);
        };

        // Use item image if available, otherwise generate a placeholder avatar
        const imageSrc = item.image || 'https://ui-avatars.com/api/?name=' + (item.name || 'Item') + '&background=random';

        let actionButtons = '';
        // Render Edit and Delete buttons ONLY for local products
        if (currentTab === 'products') {
            actionButtons = `
                <div class="flex gap-2 mt-3 pt-2 border-t border-slate-800/80">
                    <button onclick="prepareEdit(${item.id}, '${(item.name || '').replace(/'/g, "\\'")}', '${(item.description || '').replace(/'/g, "\\'")}', ${item.price || 0}, '${imageSrc}')" 
                        class="w-1/2 py-1.5 bg-amber-500/10 text-amber-400 hover:bg-amber-500/20 rounded-lg text-xs font-medium transition flex items-center justify-center gap-1">
                        <i class="fa-solid fa-pen"></i> Edit
                    </button>
                    <button onclick="deleteProduct(${item.id})" 
                        class="w-1/2 py-1.5 bg-rose-500/10 text-rose-400 hover:bg-rose-500/20 rounded-lg text-xs font-medium transition flex items-center justify-center gap-1">
                        <i class="fa-solid fa-trash"></i> Delete
                    </button>
                </div>
            `;
        }

        card.innerHTML = `
            <div class="space-y-3">
                <img src="${imageSrc}" alt="${item.name}" class="w-full h-44 object-cover rounded-xl border border-slate-800/80" onerror="this.src='https://ui-avatars.com/api/?name=Item&background=random';">
                <h3 class="font-bold text-white text-sm group-hover:text-indigo-400 transition line-clamp-1">${item.name || item.title}</h3>
                <p class="text-[11px] text-slate-400 line-clamp-1">${item.description || item.species || 'No details'}</p>
            </div>
            <div>
                <div class="pt-3 mt-3 border-t border-slate-800/80 flex justify-between items-center text-xs">
                    <span class="text-emerald-400 font-bold text-xs">${item.price ? '$' + item.price.toFixed(2) : 'View more'}</span>
                    <span class="text-slate-500 text-[11px]">View Detail →</span>
                </div>
                ${actionButtons}
            </div>
        `;
        grid.appendChild(card);
    });
}

/**
 * Opens the detail modal and populates it with item data.
 */
export function openModal(item, currentTab) {
    document.getElementById('modalBadge').innerText = currentTab.toUpperCase();
    document.getElementById('modalTitle').innerText = item.name || item.title;
    document.getElementById('modalSubtitle').innerText = item.description || item.species || 'No additional description.';
    
    const modalImage = document.getElementById('modalImage');
    const imageSrc = item.image || 'https://ui-avatars.com/api/?name=' + (item.name || 'Item') + '&background=random';
    modalImage.src = imageSrc;
    modalImage.classList.remove('hidden');

    document.getElementById('modalExtra').innerHTML = `
        <p><strong>ID:</strong> #${item.id}</p>
        ${item.status ? `<p><strong>Status:</strong> ${item.status}</p>` : ''}
        ${item.price !== undefined ? `<p><strong>Price:</strong> $${item.price.toFixed(2)}</p>` : ''}
    `;

    document.getElementById('detailModal').classList.remove('hidden');
}