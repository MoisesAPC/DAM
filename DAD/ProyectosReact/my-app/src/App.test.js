// test() y expect() son métodos de la librería Jest.
// render y screen son métodos de la librería React Testing Library
import { render, screen } from '@testing-library/react';
import App from './App';  // Importamos el componente que queremos probar

test('renders learn react link', () => {
  // Renderizamos el componente en un DOM virtual
  render(<App />);

  // Accedemos a la pantalla del componente renderizado con el método screen.
  // Y buscamos dentro de la pantalla el texto “learn react” usando el selector (o query) getByText
  const linkElement = screen.getByText(/learn react/i);

  // Le indicamos que esperamos (expect()) que en el documento esté el texto “learn react”
  expect(linkElement).toBeInTheDocument();
});
