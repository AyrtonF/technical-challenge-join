import { Link } from 'react-router-dom'
import { FaHome, FaPlus, FaList } from 'react-icons/fa'
import "./Navigation.css"

const Navigation = () => {
  return (
    <nav className="navbar">
      <div className="container">
        <Link className="brand" to="/">
          Product CRUD
        </Link>
        
        <div className="navbarNav">
          <ul>
            <li className="navbarNav">
              <Link className="navLink" to="/">
                <FaHome className="icon" /> Home
              </Link>
            </li>
            <li className="navItem">
              <Link className="navLink" to="/products">
                <FaList className="icon" /> Produtos
              </Link>
            </li>
            <li className="navItem">
              <Link className="navLink" to="/products/add">
                <FaPlus className="icon" /> Adicionar Produto
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  )
}

export default Navigation