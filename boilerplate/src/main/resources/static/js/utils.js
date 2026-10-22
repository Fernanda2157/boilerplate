/**
 * Debounce function to limit the rate at which a function is executed.
 * Prevents API flooding during typing events.
 */
export function debounce(fn, delay = 4500) {
    let timeoutId;
    return function (...args) {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => fn.apply(this, args), delay);
    };
}