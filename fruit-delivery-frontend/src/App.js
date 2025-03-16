import React, {useState} from "react";
import DeliveryForm from "./DeliveryForm";
import DeliveryReport from "./DeliveryReport";

function App() {
  const [view, setView] = useState('form')

  return (
      <div style={{ margin: '20px' }}>
        <h1>Fruit Delivery App (React)</h1>
        <div>
          <button onClick={() => setView('form')}>Новая поставка</button>
          <button onClick={() => setView('report')}>Отчёт</button>
        </div>

        {/* Рендерим один из двух компонентов, исходя из состояния */}
        {view === 'form' ? <DeliveryForm /> : <DeliveryReport />}
      </div>
  );
}

export default App;
