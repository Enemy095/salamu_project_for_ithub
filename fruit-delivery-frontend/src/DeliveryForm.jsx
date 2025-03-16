// src/DeliveryForm.js
import React, { useState, useEffect } from 'react';
import {suppliers as suppliersData, products as productsData, supplierPrices} from "./TestData"

/**
 * DeliveryForm: Форма для создания новой поставки с выпадающими списками.
 */
function DeliveryForm() {
    // Поля поставки
    const [supplierId, setSupplierId] = useState('');
    const [deliveryDate, setDeliveryDate] = useState('');

    // Массив позиций поставки
    const [items, setItems] = useState([
        { productId: '', quantity: '', pricePerUnit: '' }
    ]);

    // Списки, которые мы подгружаем с сервера (или fallback)
    const [suppliers, setSuppliers] = useState([]);
    const [products, setProducts] = useState([]);

    // Сообщение об ответе
    const [responseMessage, setResponseMessage] = useState('');

    /**
     * При монтировании компонента подгружаем список поставщиков и продуктов.
     */
    useEffect(() => {
        fetchSuppliers();
        fetchProducts();
    }, []);

    // Подгружаем поставщиков
    const fetchSuppliers = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/suppliers');
            if (!response.ok) {
                throw new Error('Ошибка сервера при получении списка поставщиков');
            }
            const data = await response.json();
            setSuppliers(data);
        } catch (error) {
            console.warn('Не удалось загрузить suppliers, используем fallback:', error);
            setSuppliers(suppliersData);
        }
    };

    // Подгружаем продукты
    const fetchProducts = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/products');
            if (!response.ok) {
                throw new Error('Ошибка сервера при получении списка продуктов');
            }
            const data = await response.json();
            setProducts(data);
        } catch (error) {
            console.warn('Не удалось загрузить products, используем fallback:', error);
        }
    };

    /**
     * Добавить новую строку items (ещё одну позицию поставки).
     */
    const handleAddItem = () => {
        setItems(prev => [
            ...prev,
            { productId: '', quantity: '', pricePerUnit: '' }
        ]);
    };

    /**
     * Изменение одного поля (productId, quantity, pricePerUnit) в конкретном item.
     */
    const handleItemChange = (index, field, value) => {
        const newItems = [...items];
        newItems[index][field] = value;
        setItems(newItems);
    };

    /**
     * Отправка формы (POST на /api/deliveries).
     */
    const handleSubmit = async (event) => {
        event.preventDefault();
        setResponseMessage('Отправка данных...');

        const requestBody = {
            supplierId: Number(supplierId),
            deliveryDate: deliveryDate,
            items: items.map(item => ({
                productId: Number(item.productId),
                weight: Number(item.quantity),
                pricePerUnit: Number(item.pricePerUnit)
            }))
        };

        try {
            const response = await fetch('http://localhost:8080/api/deliveries/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            });
            if (!response.ok) {
                throw new Error('Ошибка при сохранении поставки');
            }
            const data = await response.json();
            setResponseMessage('Поставка успешно сохранена. ID: ' + data.id);

            // Сброс формы
            setSupplierId('');
            setDeliveryDate('');
            setItems([{ productId: '', quantity: '', pricePerUnit: '' }]);
        } catch (error) {
            console.error(error);
            setResponseMessage('Ошибка: ' + error.message);
        }
    };

    return (
        <div style={{ marginTop: '20px' }}>
            <h2>Новая поставка</h2>
            <form onSubmit={handleSubmit}>
                {/* Выбор поставщика */}
                <div>
                    <label>Поставщик: </label>
                    <select
                        value={supplierId}
                        onChange={(e) => setSupplierId(e.target.value)}
                        required
                    >
                        <option value="">-- Выберите поставщика --</option>
                        {suppliers.map((supplier) => (
                            <option key={supplier.id} value={supplier.id}>
                                {supplier.name}
                            </option>
                        ))}
                    </select>
                </div>

                {/* Дата поставки */}
                <div>
                    <label>Дата поставки (YYYY-MM-DD): </label>
                    <input
                        type="date"
                        value={deliveryDate}
                        onChange={(e) => setDeliveryDate(e.target.value)}
                        required
                    />
                </div>

                {/* Список позиций поставки */}
                <div style={{ marginTop: '10px' }}>
                    <h3>Позиции поставки</h3>
                    {items.map((item, index) => (
                        <div key={index} style={{ marginBottom: '10px' }}>
                            {/* Выбор продукта */}
                            <label>Продукт: </label>
                            <select
                                value={item.productId}
                                onChange={(e) => handleItemChange(index, 'productId', e.target.value)}
                                required
                            >
                                <option value="">-- Выберите продукт --</option>
                                {products.map(prod => (
                                    <option key={prod.id} value={prod.id}>
                                        {prod.name} Тип {prod.type}
                                    </option>
                                ))}
                            </select>

                            <label style={{ marginLeft: '10px' }}>Количество (кг): </label>
                            <input
                                type="number"
                                value={item.quantity}
                                onChange={(e) => handleItemChange(index, 'quantity', e.target.value)}
                                required
                            />

                            <label style={{ marginLeft: '10px' }}>Цена за кг: </label>
                            <input
                                type="number"
                                step="0.01"
                                value={item.pricePerUnit}
                                onChange={(e) => handleItemChange(index, 'pricePerUnit', e.target.value)}
                                required
                            />
                        </div>
                    ))}
                    <button type="button" onClick={handleAddItem}>
                        Добавить позицию
                    </button>
                </div>

                <button type="submit" style={{ marginTop: '10px' }}>
                    Сохранить поставку
                </button>
            </form>

            {responseMessage && (
                <div style={{ marginTop: '10px', color: 'blue' }}>
                    {responseMessage}
                </div>
            )}
        </div>
    );
}

export default DeliveryForm;
