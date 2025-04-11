import { useState, FormEvent } from 'react'
import { FaSearch } from 'react-icons/fa'
import "./SearchBar.css"

interface SearchBarProps {
  onSearch: (term: string) => void
}

const SearchBar = ({ onSearch }: SearchBarProps) => {
  const [searchTerm, setSearchTerm] = useState('')

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault()
    onSearch(searchTerm)
  }

  return (
    <form onSubmit={handleSubmit} className="searchForm">
      <div className="searchGroup">
        <input
          type="text"
          className="searchInput"
          placeholder="Pesquise produtos..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button className="searchButton" type="submit">
          <FaSearch className="searchIcon" />
        </button>
      </div>
    </form>
  )
}

export default SearchBar