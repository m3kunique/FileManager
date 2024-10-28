document.addEventListener('DOMContentLoaded', function () {
    const renameFolderModal = document.getElementById('renameFolder');
    if (renameFolderModal) {
        renameFolderModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget; // Кнопка, которая открыла модальное окно
            const objectName = button.getAttribute('data-object-name');
            const objectPath = button.getAttribute('data-object-path');
            const objectOwner = button.getAttribute('data-object-owner');

            // Установить значение в поле ввода
            const inputName = renameFolderModal.querySelector('#name');
            inputName.value = objectName;

            // Установить значения в скрытые поля
            const inputPath = renameFolderModal.querySelector('input[name="path"]');
            inputPath.value = objectPath;

            const inputOwner = renameFolderModal.querySelector('input[name="owner"]');
            inputOwner.value = objectOwner;
        });
    }
});
