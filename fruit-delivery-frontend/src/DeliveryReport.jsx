// src/DeliveryReport.js
import React, {useState} from 'react';
import {suppliers, products, supplierPrices} from "./TestData"

/**
 * DeliveryReport: Форма для получения и отображения отчёта.
 */




function DeliveryReport() {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [reportData, setReportData] = useState([]);
    const [message, setMessage] = useState('');


    /**
     * При нажатии "Получить отчёт" - делаем GET-запрос.
     * Если сервер недоступен, можно показать сообщение об ошибке
     * или теоретически вернуть статические данные.
     */
    const handleGetReport = async () => {
        if (!startDate || !endDate) {
            setMessage('Укажите и startDate, и endDate');
            return;
        }
        setMessage('Загрузка отчёта...');

        const url = `http://localhost:8080/api/deliveries/report?start=${startDate}&end=${endDate}`;
        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('Ошибка при получении отчёта');
            }
            const data = await response.json();
            setReportData(data);
            setMessage(`Отчёт получен. Найдено записей: ${data.length}`);
        } catch (error) {
            console.error(error);
            setMessage('Ошибка: ' + error.message);
        }
    };

    return (
        <div style={{marginTop: '20px'}}>
            <h2>Отчёт по поставкам</h2>
            <div>
                <label>Дата начала (startDate): </label>
                <input
                    type="date"
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                />
            </div>
            <div>
                <label>Дата конца (endDate): </label>
                <input
                    type="date"
                    value={endDate}
                    onChange={(e) => setEndDate(e.target.value)}
                />
            </div>
            <button onClick={handleGetReport} style={{marginTop: '10px'}}>
                Получить отчёт
            </button>

            {message && (
                <div style={{marginTop: '10px', color: 'blue'}}>
                    {message}
                </div>
            )}

            {reportData.length > 0 && (
                <table border="1" style={{marginTop: '10px'}}>
                    <thead>
                    <tr>
                        <th>Поставщик</th>
                        <th>Продукт</th>
                        <th>Тип</th>
                        <th>Всего (кг)</th>
                        <th>Общая стоимость</th>
                    </tr>
                    </thead>
                    <tbody>
                    {reportData.map((item, index) => (
                        <tr key={index}>
                            <td>{item.supplierName}</td>
                            <td>{item.productName}</td>
                            <td>{item.typeName}</td>
                            <td>{item.totalQuantity}</td>
                            <td>{item.totalCost}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default DeliveryReport;
