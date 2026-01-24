import axios from 'axios';
import { useState } from 'react';
import './App.css';

function App() {
  const [data,setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const handleClick = async () => {
    setLoading(true);
    try{
      const res = await axios.get("http://localhost:8181/abc",{
      headers: {
        "X-Custom-test-Header": "my-custom-value" // triggers OPTIONS preflight
      },
      withCredentials: true  // send cookies/session
    });

      const result = res.data;
      setData(result);
    }catch(error){
      console.error(error)
    }finally{
      setLoading(false);
    }
  }

  return (
    <>
        <div style={{ padding: "20px" }}>
      <h1>Fetch Data Example</h1>

      <button onClick={handleClick}>
        Fetch User
      </button>

      {loading && <p>Loading...</p>}

      {data && (
        <pre>{JSON.stringify(data, null, 2)}</pre>
      )}
    </div>
    </>
  )
}

export default App
