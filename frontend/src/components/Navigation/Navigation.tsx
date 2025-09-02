import { useState } from "react";
import { Link } from "react-router-dom";
import { FaHome, FaPlus, FaList, FaBars, FaTimes } from "react-icons/fa";
import "./Navigation.css";

const Navigation = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const closeMenu = () => {
    setIsMenuOpen(false);
  };

  return (
    <nav className="navbar">
      <div className="container">
        <Link className="brand" to="/" onClick={closeMenu}>
          Product CRUD
        </Link>

        <button
          className="menuToggle"
          onClick={toggleMenu}
          aria-label={isMenuOpen ? "Fechar menu" : "Abrir menu"}
          aria-expanded={isMenuOpen}
        >
          {isMenuOpen ? <FaTimes /> : <FaBars />}
        </button>

        <div className={`navbarNav ${isMenuOpen ? "show" : ""}`}>
          <ul>
            <li className="navItem">
              <Link className="navLink" to="/" onClick={closeMenu}>
                <FaHome className="icon" /> Home
              </Link>
            </li>
            <li className="navItem">
              <Link className="navLink" to="/products" onClick={closeMenu}>
                <FaList className="icon" /> Produtos
              </Link>
            </li>
            <li className="navItem">
              <Link className="navLink" to="/products/add" onClick={closeMenu}>
                <FaPlus className="icon" /> Adicionar Produto
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navigation;
